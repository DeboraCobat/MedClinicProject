package teamproject.medclinic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import teamproject.medclinic.entity.Appointments;
import teamproject.medclinic.repository.AppointmentRepo;

import java.util.List;

@Controller
public class AppointmentsController {
    private final AppointmentRepo appointmentRepo;

    public AppointmentsController(AppointmentRepo appointmentRepo) {
        this.appointmentRepo = appointmentRepo;
    }

    // List ALL Operation (HTTP GET)
    @GetMapping("/admin/appointmentsList")
    public String appointmentsList(Model model) {
        List<Appointments> appointments = appointmentRepo.findAll();
        model.addAttribute("appointments", appointments);
        return "admin/appointmentsList";
    }

 // Create Operation (HTTP POST)

//    @PostMapping
//    public ResponseEntity<Users> createDoctor(@RequestBody Users doctor) {
//        doctor.setRole(Users.Role.doctor);
//        Users savedDoctor = userRepo.save(doctor);
//        return ResponseEntity.status(HttpStatus.CREATED).body(savedDoctor);
//    }

}