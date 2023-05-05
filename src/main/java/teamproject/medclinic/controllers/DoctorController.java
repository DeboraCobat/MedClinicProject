package teamproject.medclinic.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import teamproject.medclinic.entity.User;
import teamproject.medclinic.repository.UserRepo;

@Controller
public class DoctorController {
    private final UserRepo userRepo;

    public DoctorController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    // List ALL Operation (HTTP GET)
    @GetMapping("/admin/doctorsList")
    public String doctorsList(Model model) {
        List<User> doctors = userRepo.findByRole(User.Role.doctor);
        model.addAttribute("doctors", doctors);
        return "admin/doctorsList";
    }

    // Create Operation (HTTP POST)
    @PostMapping
    public ResponseEntity<Users> doctorCreate (@RequestBody Users doctor) {
        doctor.setRole(Users.Role.doctor);
        Users savedDoctor = userRepo.save(doctor);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDoctor);
    }
}