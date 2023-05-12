package teamproject.medclinic.controllers;

import jakarta.servlet.http.HttpSession;
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

    //DASHBOARD
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<User> admin = userRepo.findAll();
        model.addAttribute("admin", admin);
        return "admin/dashboard";
    }

    //// DOCTOR CRUD ////

    // List ALL Operation
    @GetMapping("/doctorsList")
    public String doctorsList(Model model) {
        List<User> doctors = userRepo.findByRole(User.Role.doctor);
        model.addAttribute("doctors", doctors);
        return "admin/doctorsList";
    }

    // Create Operation
    @RequestMapping("/doctorCreate")
    public String doctorCreate(Model model) {
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

    // Update Operation
    @GetMapping("/doctorUpdate/{id}")
    public String doctorUpdate(@PathVariable("id") Long id, Model model) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        return "admin/doctorUpdate";
    }

    @PostMapping("/docUpdate/{id}")
//    public String docUpdate(@PathVariable("id") Long id, @ModelAttribute("user") User user) {
//        User existingDoctor = userRepo.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
//        existingDoctor.setFirst_name(user.getFirst_name());
//        existingDoctor.setLast_name(user.getLast_name());
//        existingDoctor.setAddress(user.getAddress());
//        existingDoctor.setPhone_number(user.getPhone_number());
//        existingDoctor.setEmail(user.getEmail());
//        existingDoctor.setSpecialty(user.getSpecialty());
//
//        userRepo.save(existingDoctor);
//
//        return "redirect:/admin/doctorsList";
//    }
//
    public String docUpdate(@ModelAttribute("user") User updatedUser, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/admin/doctorsList";
        }
        User existingUser = userRepo.findById(user.getId()).orElse(null);
        if (existingUser == null) {
            return "redirect:/admin/doctorsList";
        }

        existingUser.setFirst_name(updatedUser.getFirst_name());
        existingUser.setLast_name(updatedUser.getLast_name());
        existingUser.setDate_of_birth(updatedUser.getDate_of_birth());
        existingUser.setAddress(updatedUser.getAddress());
        existingUser.setPhone_number(updatedUser.getPhone_number());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setGender(updatedUser.getGender());
        userRepo.save(existingUser);
        session.setAttribute("user", existingUser);

        return "redirect:/admin/doctorsList";
    }

    // DOCTOR Delete Operation
    @RequestMapping("/doctorDelete/{id}")
    public String deleteDoctor(@PathVariable("id") Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid appointment id: " + id));
        userRepo.delete(user);
        return "redirect:/admin/doctorsList";
    }

    ////// PATIENT CRUD ////

    // List ALL Operation
    @GetMapping("/patientsList")
    public String patientsList(Model model) {
        List<User> patients = userRepo.findByRole(User.Role.patient);
        model.addAttribute("patients", patients);
        return "admin/patientsList";
    }

    // Create Operation
    @RequestMapping("/patientCreate")
    public String patientCreate(Model model) {
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

    //PATIENT Update Operation
    @GetMapping("/patientUpdate/{id}")
    public String patientUpdate(@PathVariable("id") Long id, Model model) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        return "admin/patientUpdate";
    }

    @PostMapping("/patUpdate/{id}")
    public String patUpdate(@PathVariable("id") Long id, @ModelAttribute("user") User user) {
        User existingPatient = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        ;

        existingPatient.setFirst_name(user.getFirst_name());
        existingPatient.setLast_name(user.getLast_name());
        existingPatient.setDate_of_birth(user.getDate_of_birth());
        existingPatient.setAddress(user.getAddress());
        existingPatient.setPhone_number(user.getPhone_number());
        existingPatient.setEmail(user.getEmail());

        userRepo.save(existingPatient);

        return "redirect:/admin/patientsList";
    }

    // PATIENT Delete Operation
    @RequestMapping("/patientDelete/{id}")
    public String patientDelete(@PathVariable("id") Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid appointment id: " + id));
        userRepo.delete(user);
        return "redirect:/admin/patientsList";
    }

    ////CRUD FOR APPOINTMENTS////

    // List ALL Operation

//    @GetMapping("/appointmentsList")
//    public String appointmentsList(Model model) {
//        List<Appointments> appointments = appointmentRepo.findAll();
//        model.addAttribute("appointments", appointments);
//        return "admin/appointmentsList";
//    }

    // Create Operation
//    @GetMapping("/appointmentCreate")
//    public String appointmentCreate (Model model, HttpSession session) {
//        User user = (User) session.getAttribute("user");
//        if (user == null) {
//            model.addAttribute("errorBook", "Account required for scheduling.");
//            model.addAttribute("user", new User());
//            return "admin/appointmentsList";
//        }
//        List<User> doctors = userRepo.findByRole(User.Role.doctor);
//        model.addAttribute("user", user);
//        model.addAttribute("appointment", new Appointments());
//        model.addAttribute("doctors", doctors);
//        return "admin/appointmentCreate";
    }

//    @PostMapping("/appointmentCreate")
//    public String bookAppointment(@ModelAttribute("appointment") Appointments appointment, HttpSession session) {
//        User user = (User) session.getAttribute("user");
//        if (user == null) {
//            return "redirect:admin/appointmentsList";
//        }
//
//        User selectedDoctor = userRepo.findById(appointment.getDoctor().getId()).orElse(null);
//        if (selectedDoctor == null || selectedDoctor.getRole() != User.Role.doctor) {
//            return "redirect:/admin/appointmentCreate";
//        }
//
//        appointment.setPatient(user);
//        appointment.setDoctor(selectedDoctor);
//        appointmentRepo.save(appointment);
//
//        return "redirect:admin/appointmentsList";
//    }



//
//    @GetMapping("/appointmentCreate")
//    public String appointmentCreate(Model model) {
//        model.addAttribute("appointment", new Appointments());
//        return "admin/appointmentCreate";
//    }
//
//    @PostMapping("/appointmentCreate")
//    public String appointmentCreate(@ModelAttribute Appointments appointment) {
//        appointmentRepo.save(appointment);
//        return "redirect:/admin/appointmentsList";
//    }
//
