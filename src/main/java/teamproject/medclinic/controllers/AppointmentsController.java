package teamproject.medclinic.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import teamproject.medclinic.entity.Appointments;
import teamproject.medclinic.entity.User;
import teamproject.medclinic.repository.AppointmentRepo;
import teamproject.medclinic.repository.UserRepo;

import java.util.List;

@Controller
public class AppointmentsController {
    private final AppointmentRepo appointmentRepo;
    private UserRepo userRepo;

    public AppointmentsController(AppointmentRepo appointmentRepo, UserRepo userRepo) {
        this.appointmentRepo = appointmentRepo;
        this.userRepo = userRepo;
    }


    //// APPOINTMENTS CRUD ////
    @GetMapping("/admin/appointmentsList")
    public String appointmentsList(Model model) {
        List<Appointments> appointments = appointmentRepo.findAll();
        model.addAttribute("appointments", appointments);
        return "admin/appointmentsList";
    }

    @GetMapping("/admin/appointmentCreate")
    public String appointmentCreate(Model model) {
        model.addAttribute("appointment", new Appointments());
        return "admin/appointmentCreate";
    }

    @PostMapping("/admin/appointmentCreate")
    public String appointmentCreate(@ModelAttribute Appointments appointment) {
        appointmentRepo.save(appointment);
        return "redirect:/admin/appointmentsList";
    }

    @GetMapping("/admin/appointmentUpdate{id}")
    public String appointmentUpdate(@PathVariable("id") Long id, Model model) {
        Appointments appointment = appointmentRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid appointment id: " + id));
        model.addAttribute("appointment", appointment);
        return "admin/appointmentUpdate";
    }

    @PutMapping ("/admin/appointmentsList{id}")
    public String appointmentUpdate(@PathVariable("id") Long id, @ModelAttribute Appointments appointment) {
        appointmentRepo.save(appointment);
        return "redirect:/admin/appointmentsList";
    }

    @GetMapping("/admin/appointmentDelete/{id}")
    public String appointmentDelete(@PathVariable("id") Long id) {
        Appointments appointment = appointmentRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid appointment id: " + id));
        appointmentRepo.delete(appointment);
        return "redirect:/admin/appointmentsList";
    }

    @GetMapping("/bookAppointment")
    public String showBookAppointmentPage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            model.addAttribute("errorBook", "Account required for scheduling.");
            model.addAttribute("user", new User());
            return "login";
        }
        List<User> doctors = userRepo.findByRole(User.Role.doctor);
        model.addAttribute("user", user);
        model.addAttribute("appointment", new Appointments());
        model.addAttribute("doctors", doctors);
        return "bookAppointment";
    }

    @PostMapping("/bookAppointment")
    public String bookAppointment(@ModelAttribute("appointment") Appointments appointment, HttpSession session, RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        User selectedDoctor = userRepo.findById(appointment.getDoctor().getId()).orElse(null);
        if (selectedDoctor == null || selectedDoctor.getRole() != User.Role.doctor) {
            return "redirect:/bookAppointment";
        }

        appointment.setPatient(user);
        appointment.setDoctor(selectedDoctor);
        appointmentRepo.save(appointment);

        redirectAttributes.addFlashAttribute("book", "Appointment scheduled!");
        return "redirect:/profile";
    }

    @GetMapping("/appointmentConfirmation")
    public String confirm(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            model.addAttribute("errorBook", "Account required to schedule an appointment.");
            return "register";
        }

        model.addAttribute("message", "Appointment Scheduled!");
        return "appointmentConfirmation";
    }
}