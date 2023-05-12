package teamproject.medclinic.controllers;

import ch.qos.logback.core.LayoutBase;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import teamproject.medclinic.entity.Appointments;
import teamproject.medclinic.entity.User;
import teamproject.medclinic.repository.AppointmentRepo;
import teamproject.medclinic.repository.UserRepo;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;

import java.security.Principal;
import java.util.List;


@Controller
public class UserController {

    @Autowired
    private UserRepo userRepo;
    private AppointmentRepo appointmentRepo;

    public UserController(AppointmentRepo appointmentRepo, UserRepo userRepo) {
        this.appointmentRepo = appointmentRepo;
        this.userRepo = userRepo;
    }

//    @GetMapping({"/", "/home"})
//    public String showHome(Model model, HttpSession session) {
//        User user = (User) session.getAttribute("user");
//        model.addAttribute("user", user);
//        return "home";
//    }

    @GetMapping({"/", "/home"})
    public String showHome(Model model, HttpSession session, Principal principal) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);

//        if (principal != null) {
//            String loggedInAs = "User: " + principal.getName();
//            model.addAttribute("loggedInAs", loggedInAs);
//        } else {
//            model.addAttribute("loggedInAs", "Register/Sign in");
//        }

        return "home";
    }

    @GetMapping("/contact")
    public String contactUs(Model model) {
        model.addAttribute("message", "Contact us");
        return "contact";
    }

    @GetMapping("/register")
    public ModelAndView register() {
        ModelAndView mav = new ModelAndView("register");
        User newUser = new User();
        mav.addObject("user", newUser);
        return mav;
    }


    @PostMapping("/saveUser")
    public ModelAndView saveUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            ModelAndView mav = new ModelAndView("register");
            mav.addObject("user", user);
            return mav;
        } else {
            user.setRole(User.Role.patient);
            userRepo.save(user);
            redirectAttributes.addFlashAttribute("create", "Your account has been successfully created!");
            return new ModelAndView("redirect:/");
        }
    }


    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        HttpSession session,
                        Model model) {
        if (email == null || password == null) {
            model.addAttribute("errorLogin", "Please provide both email and password");
            return "login";
        }

        User user = userRepo.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("user", user);

            if (user.getRole() == User.Role.patient) {
                return "redirect:/profile";
            } else if (user.getRole() == User.Role.doctor) {
                return "redirect:/doctor/profileDoctor";
            } else if (user.getRole() == User.Role.admin) {
                return "redirect:/admin/dashboard";
            }
        }
            model.addAttribute("error", "Invalid email or password");
            return "login";

    }



    @PostMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("logout", "Logged out");
        return "redirect:/";
    }

    @GetMapping("/profile")
    public String showProfile(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "You are not logged in");
            return "login";
        }
        List<Appointments> appointments = appointmentRepo.findByDoctorIdOrPatientId(user.getId(), user.getId());
        model.addAttribute("appointments", appointments);
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/editProfile")
    public String showEditProfileForm(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "You are not logged in");
            return "login";
        }
        model.addAttribute("user", user);
        return "editProfile";
    }


    @PostMapping("/editProfile")
    public String editProfile(@ModelAttribute("user") User updatedUser, HttpSession session, RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        // Retrieve the existing user from the database
        User existingUser = userRepo.findById(user.getId()).orElse(null);
        if (existingUser == null) {
            // Handle the case where the user is not found in the database
            return "redirect:/login";
        }

        // Update the user properties with the new values
        existingUser.setFirst_name(updatedUser.getFirst_name());
        existingUser.setLast_name(updatedUser.getLast_name());
        existingUser.setDate_of_birth(updatedUser.getDate_of_birth());
        existingUser.setAddress(updatedUser.getAddress());
        existingUser.setPhone_number(updatedUser.getPhone_number());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setGender(updatedUser.getGender());

        // Save the updated user back to the database
        userRepo.save(existingUser);

        // Update the user object in the session with the updated values
        session.setAttribute("user", existingUser);

        redirectAttributes.addFlashAttribute("editSuccess", "Account updated!");
        return "redirect:/profile";
    }

    @GetMapping("/deleteAccount")
    public String showDeleteAccountConfirmation(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "You are not logged in");
            return "login";
        }
        model.addAttribute("user", user);
        return "deleteAccount";
    }

    @PostMapping("/deleteAccount")
    public String deleteAccount(HttpSession session, RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        try {
            userRepo.delete(user);

            // Clear the session and redirect the user to the login page or any other appropriate page
            session.invalidate();

            redirectAttributes.addFlashAttribute("successMessage", "Your account has been successfully deleted.");
            return "redirect:/login";
        } catch (DataIntegrityViolationException ex) {
            // Handle the constraint violation exception

            redirectAttributes.addFlashAttribute("errorMessage", "Cannot delete the account due to associated appointments.");
            return "redirect:/profile";
        }
    }

    @GetMapping("/deleteAppointment/{id}")
    public String showDeleteAppointmentConfirmation(Model model, HttpSession session, @PathVariable("id") Long appointmentId) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "You are not logged in");
            return "login";
        }
        model.addAttribute("user", user);
        model.addAttribute("appointmentId", appointmentId);
        return "deleteAppointment";
    }

    @PostMapping("/deleteAppointment/{id}")
    public String deleteAppointment(@PathVariable("id") Long appointmentId, HttpSession session, RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        // Retrieve the user's appointments from the database
        List<Appointments> userAppointments = appointmentRepo.findByPatientId(user.getId());

        // Find the appointment with the matching ID
        Appointments appointmentToDelete = null;
        for (Appointments appointment : userAppointments) {
            if (appointment.getId().equals(appointmentId)) {
                appointmentToDelete = appointment;
                break;
            }
        }

        if (appointmentToDelete != null) {
            // Delete the appointment
            appointmentRepo.delete(appointmentToDelete);

            // Remove the appointment from the user's list
            userAppointments.remove(appointmentToDelete);

            redirectAttributes.addFlashAttribute("cancel", "Appointment canceled");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Appointment not found");
        }

        return "redirect:/profile";
    }

}
