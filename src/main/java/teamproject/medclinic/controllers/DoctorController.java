package teamproject.medclinic.controllers;

import java.io.IOException;
import java.util.List;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;
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

//    // Create Operation (HTTP POST)
//    @GetMapping("/admin/doctorCreate")
//    public String doctorCreateForm() {
//        return "admin/doctorCreate";
//    }
//
//    @PostMapping("/admin/doctorCreate")
//    public ResponseEntity<User> doctorCreate(@RequestBody User doctor) {
//        doctor.setRole(User.Role.doctor);
//        User savedDoctor = userRepo.save(doctor);
//        return ResponseEntity.status(HttpStatus.CREATED).body(savedDoctor);
//    }
//
//    @ExceptionHandler(IOException.class)
//    public ResponseEntity<String> handleIOException(IOException e) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request body");
//    }
//}
    @RequestMapping("/admin/doctorCreate")
    public String doctorCreate(Model model) {
        User user = new User();
        user.setRole(User.Role.doctor);
        model.addAttribute("user", user);
        return "admin/doctorCreate";
    }

    @PostMapping("/admin/doctorCreate")
    public String doctorCreate(@ModelAttribute("user") User user) {
        user.setRole(User.Role.doctor);
        userRepo.save(user);
        return "redirect:/admin/doctorsList";
    }
}
