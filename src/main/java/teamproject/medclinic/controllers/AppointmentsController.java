package teamproject.medclinic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import teamproject.medclinic.entity.Appointments;
import teamproject.medclinic.repository.AppointmentRepo;

import java.util.List;

@Controller
public class AppointmentsController {
    private final AppointmentRepo appointmentRepo;

    public AppointmentsController(AppointmentRepo appointmentRepo) {
        this.appointmentRepo = appointmentRepo;
    }

//
//    // List ALL Operation (HTTP GET)
//    @GetMapping("/admin/appointmentsList")
//    public String appointmentsList(Model model) {
//        List<Appointments> appointments = appointmentRepo.findAll();
//        model.addAttribute("appointments", appointments);
//        return "admin/appointmentsList";
//    }
//}
// // Create Operation (HTTP POST)
//
////    @RequestMapping(value = "/admin/appointmentCreate", method = RequestMethod.GET)
////    public String appointmentCreate() {
////        return "admin/appointmentCreate";
////    }
////
////    @PostMapping("/admin/appointmentCreate")
////    public ResponseEntity<Users> appointmentCreate(@RequestBody Users doctor) {
////        doctor.setRole(Users.Role.doctor);
////        Users savedDoctor = userRepo.save(doctor);
////        return ResponseEntity.status(HttpStatus.CREATED).body(savedDoctor);
////    }

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
}