package teamproject.medclinic.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import teamproject.medclinic.entity.Appointments;
import teamproject.medclinic.entity.Doctor;
import teamproject.medclinic.entity.User;
import teamproject.medclinic.repository.AppointmentRepo;
import teamproject.medclinic.repository.UserRepo;
import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Controller
public class AppointmentsController {
    private final AppointmentRepo appointmentRepo;
    private UserRepo userRepo;

    public AppointmentsController(AppointmentRepo appointmentRepo, UserRepo userRepo) {
        this.appointmentRepo = appointmentRepo;
        this.userRepo = userRepo;
    }


    // List ALL Operation (HTTP GET)
    @GetMapping("/admin/appointmentsList")
    public String appointmentsList(Model model) {
        List<Appointments> appointments = appointmentRepo.findAll();
        model.addAttribute("appointments", appointments);
        return "admin/appointmentsList";
    }

    //for users

//    @GetMapping("/bookAppointment")
//    public String showBookAppointmentPage(Model model, HttpSession session) {
//        User user =(User) session.getAttribute("user");
//        if (user == null) {
//            model.addAttribute("errorBook", "Account required for scheduling.");
//            model.addAttribute("user", new User()); // Add this line to provide a User object to the template
//            return "registration";
//        }
//        List<User> doctors = userRepo.findByRole(User.Role.doctor);
//        model.addAttribute("user", user);
//        model.addAttribute("appointment", new Appointments());
//        model.addAttribute("doctors", doctors);
//        return "bookAppointment";
//    }
//
//    @PostMapping("/bookAppointment")
//    public String bookAppointment(@ModelAttribute("appointment") Appointments appointment, HttpSession session, Model model) {
//        User user = (User) session.getAttribute("user");
//        if (user == null) {
//            model.addAttribute("errorBook", "Account required to schedule an appointment.");
//            return "registration";
//        }
//
//        // Save the appointment to the database
//        appointmentRepository.save(appointment);
//        // Redirect to a confirmation page or any other desired page
//        return "redirect:/appointmentConfirmation";
//    }
//
//    @GetMapping("/appointmentConfirmation")
//    public String confirm(Model model, HttpSession session){
//        User user = (User) session.getAttribute("user");
//        if (user == null) {
//            model.addAttribute("errorBook", "Account required to schedule an appointment.");
//            return "registration";
//        }
//
//        model.addAttribute("message" , "Appointment Scheduled!");
//        return "appointmentConfirmation";
//    }

    @GetMapping("/bookAppointment")
    public String showBookAppointmentPage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            model.addAttribute("errorBook", "Account required for scheduling.");
            model.addAttribute("user", new User()); // Add this line to provide a User object to the template
            return "registration";
        }
        List<User> doctors = userRepo.findByRole(User.Role.doctor);
        model.addAttribute("user", user);
        model.addAttribute("appointment", new Appointments());
        model.addAttribute("doctors", doctors);
        return "bookAppointment";
    }

//    @PostMapping("/bookAppointment")
//    public String bookAppointment(@ModelAttribute("appointment") Appointments appointment, HttpSession session, Model model, @RequestParam("doctor") Long doctorId, @RequestParam("appointmentTime") String appointmentTime, @RequestParam("notes") String notes) {
//        User user = (User) session.getAttribute("user");
//        if (user == null) {
//            model.addAttribute("errorBook", "Account required to schedule an appointment.");
//            return "registration";
//        }
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
//        LocalDateTime appointmentDateTime = LocalDateTime.parse(appointmentTime, formatter);
//        Date appointmentTimeString = Date.from(appointmentDateTime.atZone(ZoneId.systemDefault()).toInstant());
//
//        // Set the patient of the appointment to the current user
//        appointment.setPatient(user);
//
////        // Fetch the Doctor object
////        User doctor = (User) userRepo.findFirstByRole(User.Role.doctor);
//
//        // Set the doctor, appointment time, and notes in the appointment object
//        appointment.setDoctor(userRepo.findById(doctorId).orElse(null));
//        appointment.setAppointmentTime(appointmentTimeString);
//        appointment.setNotes(notes);
//
//        // Save the appointment to the database
//        appointmentRepo.save(appointment);
//
//        return "redirect:/appointmentConfirmation"; // Redirect to the appointment confirmation page
//    }

    @PostMapping("/bookAppointment")
    public String bookAppointment(@ModelAttribute("appointment") Appointments appointment, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login"; // Redirect to login page if user is not logged in
        }

        User selectedDoctor = userRepo.findById(appointment.getDoctor().getId()).orElse(null);
        if (selectedDoctor == null || selectedDoctor.getRole() != User.Role.doctor) {
            return "redirect:/bookAppointment"; // Redirect back to the bookAppointment page if the selected doctor is invalid
        }

        appointment.setPatient(user);
        appointment.setDoctor(selectedDoctor);
        appointmentRepo.save(appointment);

        return "redirect:/appointmentConfirmation"; // Redirect to appointments page or any other desired page
    }

    @GetMapping("/appointmentConfirmation")
    public String confirm(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            model.addAttribute("errorBook", "Account required to schedule an appointment.");
            return "registration";
        }

        model.addAttribute("message", "Appointment Scheduled!");
        return "appointmentConfirmation";
    }
}
 // Create Operation (HTTP POST)

//    @RequestMapping(value = "/admin/appointmentCreate", method = RequestMethod.GET)
//    public String appointmentCreate() {
//        return "admin/appointmentCreate";
//    }
//
//    @PostMapping("/admin/appointmentCreate")
//    public ResponseEntity<Users> appointmentCreate(@RequestBody Users doctor) {
//        doctor.setRole(Users.Role.doctor);
//        Users savedDoctor = userRepo.save(doctor);
//        return ResponseEntity.status(HttpStatus.CREATED).body(savedDoctor);
//    }