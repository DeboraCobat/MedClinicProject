//URLs ON THIS CONTROLLER
//"/admin/dashboard" GET ok
//"/admin/doctorsList" GET ok
//"/admin/doctorCreate" GET ok
//"/admin/doctorUpdate/{id}" GET ok
//"/admin/updateDoctor/{id}" POST  ok
//"/admin/deleteUser/{id}" GET error 404
//"/admin/patientsList" GET ok
//"/admin/patientCreate" GET ok
//"/admin/patientUpdate/{id}" GET error 500
//"/admin/patientDelete/{id}" GET error 500
//"/admin/appointmentsList" GET ok
//"/admin/appointmentCreate" GET ok
//"/admin/appointmentUpdate/{id}" GET error 500
//"/admin/appointmentDelete/{id}" GET ok

package teamproject.medclinic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import teamproject.medclinic.entity.Appointments;
import teamproject.medclinic.entity.User;
import teamproject.medclinic.repository.AppointmentRepo;
import teamproject.medclinic.repository.UserRepo;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserRepo userRepo;
    private final AppointmentRepo appointmentRepo;

    public AdminController(UserRepo userRepo, AppointmentRepo appointmentRepo) {
        this.userRepo = userRepo;
        this.appointmentRepo = appointmentRepo;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<User> admin = userRepo.findAll();
        model.addAttribute("admin", admin);
        return "admin/dashboard";
    }

    //// DOCTOR CRUD ////

    @GetMapping("/doctorsList")
    public String doctorsList(Model model) {
        List<User> doctors = userRepo.findByRole(User.Role.doctor);
        model.addAttribute("doctors", doctors);
        return "admin/doctorsList";
    }

    // DOCTOR Create Operation
    @GetMapping("/doctorCreate")
    public String doctorCreateForm(Model model) {
        User user = new User();
        user.setRole(User.Role.doctor);
        model.addAttribute("user", user);
        return "admin/doctorCreate";
    }

    @PostMapping("/doctorCreate")
    public String doctorCreate(@ModelAttribute("user") User user) {
        user.setRole(User.Role.doctor);
        userRepo.save(user);
        return "redirect:/admin/doctorsList";
    }

    // DOCTOR Update Operation
    @GetMapping("/doctorUpdate/{id}")
    public String doctorUpdate(@PathVariable("id") Long id, Model model) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        return "admin/doctorUpdate";
    }

    @PostMapping("/updateDoctor/{id}")
    public String updateDoctor(@PathVariable("id") Long id, @ModelAttribute("user") User user) {
        User existingDoctor = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        existingDoctor.setFirstName(user.getFirstName());
        existingDoctor.setLastName(user.getLastName());
        existingDoctor.setDateOfBirth(user.getDateOfBirth());
        existingDoctor.setAddress(user.getAddress());
        existingDoctor.setPhoneNumber(user.getPhoneNumber());
        existingDoctor.setEmail(user.getEmail());
        existingDoctor.setSpecialty(user.getSpecialty());

        userRepo.save(existingDoctor);

        return "redirect:/admin/doctorsList";
    }

    // DOCTOR Delete Operation
    @GetMapping("/admin/deleteUser/{id}")
    public String deleteUser(@PathVariable Long id) {
        User user = userRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user id"));
        userRepo.delete(user);

        if (user.getRole() == User.Role.patient) {
            return "redirect:/admin/patientsList";
        } else if (user.getRole() == User.Role.doctor) {
            return "redirect:/admin/doctorsList";
        } else {
            throw new IllegalArgumentException("Invalid user role");
        }
    }

        //// PATIENT CRUD ////

        // PATIENT List ALL Operation
        @GetMapping("/patientsList")
        public String patientsList(Model model) {
            List<User> patients = userRepo.findByRole(User.Role.patient);
            model.addAttribute("patients", patients);
            return "admin/patientsList";
        }

        // PATIENT Create Operation
        @GetMapping("/patientCreate")
        public String patientCreateForm(Model model) {
            User user = new User();
            user.setRole(User.Role.patient);
            model.addAttribute("user", user);
            return "admin/patientCreate";
        }

        @PostMapping("/patientCreate")
        public String patientCreate(@ModelAttribute("user") User user) {
            user.setRole(User.Role.patient);
            userRepo.save(user);
            return "redirect:/admin/patientsList";
        }

        // PATIENT Update Operation
        @GetMapping("/patientUpdate/{id}")
        public String patientUpdate(@PathVariable("id") Long id, ModelMap model) {
            User user = userRepo.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
            model.addAttribute("user", user);
            return "admin/patientUpdate";
        }

        @PostMapping("/patientUpdate/{id}")
        public String patientUpdate(@PathVariable("id") Long id, @ModelAttribute("user") User updatedUser) {
            User existingUser = userRepo.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setLastName(updatedUser.getLastName());
            existingUser.setDateOfBirth(updatedUser.getDateOfBirth());
            existingUser.setAddress(updatedUser.getAddress());
            existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
            existingUser.setEmail(updatedUser.getEmail());
            userRepo.save(existingUser);
            return "redirect:/admin/patientsList";
        }

        // PATIENT Delete Operation
        @GetMapping("/patientDelete/{id}")
        public String patientDelete(@PathVariable("id") Long id) {
            userRepo.deleteById(id);
            return "redirect:/admin/patientsList";
        }

        //// APPOINTMENTS CRUD ////
        @GetMapping("/appointmentsList")
        public String appointmentsList(Model model) {
            List<Appointments> appointments = appointmentRepo.findAll();
            model.addAttribute("appointments", appointments);
            return "admin/appointmentsList";
        }

        @GetMapping("/appointmentCreate")
        public String appointmentCreate(Model model) {
            model.addAttribute("appointment", new Appointments());
            return "admin/appointmentCreate";
        }

        @PostMapping("/appointmentCreate")
        public String appointmentCreate(@ModelAttribute Appointments appointment) {
            appointmentRepo.save(appointment);
            return "redirect:/admin/appointmentsList";
        }

        @GetMapping("/appointmentUpdate/{id}")
        public String appointmentUpdate(@PathVariable("id") Long id, Model model) {
            Appointments appointment = appointmentRepo.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid appointment id: " + id));
            model.addAttribute("appointment", appointment);
            return "admin/appointmentUpdate";
        }

        @PostMapping("/appointmentUpdate/{id}")
        public String appointmentUpdate(@PathVariable("id") Long id, @ModelAttribute Appointments appointment) {
            appointmentRepo.save(appointment);
            return "redirect:/admin/appointmentsList";
        }

        @GetMapping("/appointmentDelete/{id}")
        public String appointmentDelete(@PathVariable("id") Long id) {
            Appointments appointment = appointmentRepo.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid appointment id: " + id));
            appointmentRepo.delete(appointment);
            return "redirect:/admin/appointmentsList";
        }
    }

 //URLs ON THIS CONTROLLER

//"/admin/dashboard"

//"/admin/doctorsList"
//"/admin/doctorCreate"

//"/admin/doctorUpdate/{id}"
//"/admin/updateDoctor/{id}"

//"/admin/deleteUser/{id}"

//"/admin/patientsList"
//"/admin/patientCreate"

//"/admin/patientUpdate/{id}"
//"/admin/patientDelete/{id}"

//"/admin/appointmentsList"
//"/admin/appointmentCreate"
//"/admin/appointmentUpdate/{id}"
//"/admin/appointmentDelete/{id}"