//package teamproject.medclinic.controllers;
//
//import java.io.IOException;
//import java.util.List;
//import org.springframework.http.HttpStatus;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import teamproject.medclinic.entity.Users;
//import teamproject.medclinic.repository.UserRepo;
//
//@Controller
//public class DoctorController {
//    private final UserRepo userRepo;
//
//    public DoctorController(UserRepo userRepo) {
//        this.userRepo = userRepo;
//    }
//
//    // List ALL Operation (HTTP GET)
//    @GetMapping("/admin/doctorsList")
//    public String doctorsList(Model model) {
//        List<Users> doctors = userRepo.findByRole(Users.Role.doctor);
//        model.addAttribute("doctors", doctors);
//        return "admin/doctorsList";
//    }
//
//    // Create Operation (HTTP POST)
//    @GetMapping("/admin/doctorCreate")
//    public String doctorCreateForm() {
//        return "admin/doctorCreate";
//    }
//
//    @PostMapping("/admin/doctorCreate")
//    public ResponseEntity<Users> doctorCreate(@RequestBody Users doctor) {
//        doctor.setRole(Users.Role.doctor);
//        Users savedDoctor = userRepo.save(doctor);
//        return ResponseEntity.status(HttpStatus.CREATED).body(savedDoctor);
//    }
//
//    @ExceptionHandler(IOException.class)
//    public ResponseEntity<String> handleIOException(IOException e) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request body");
//    }
//}
package teamproject.medclinic.controllers;

import java.io.IOException;
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
    @GetMapping("/admin/doctorCreate")
    public String doctorCreateForm() {
        return "admin/doctorCreate";
    }

    @PostMapping("/admin/doctorCreate")
    public ResponseEntity<Users> doctorCreate(@RequestBody Users doctor) {
        if (doctor != null) {
            doctor.setRole(Users.Role.doctor);
        }
        Users savedDoctor = userRepo.save(doctor);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDoctor);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request body");
    }
}
