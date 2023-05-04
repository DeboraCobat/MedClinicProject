package teamproject.medclinic.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import teamproject.medclinic.entity.Users;
import teamproject.medclinic.repository.UserRepo;
import teamproject.medclinic.services.DoctorService;

@Controller
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private UserRepo userRepo;

    // List ALL Operation (HTTP GET)

        @GetMapping
        public String getAllDoctors(Model model) {
            List<Users> doctors = doctorService.getAllDoctors();
            model.addAttribute("doctors", doctors);
            return "doctorsList";
        }

        @GetMapping("/{id}")
        public String getDoctorById(@PathVariable Long id, Model model) {
            Optional<Users> doctor = doctorService.getDoctorById(id);
            model.addAttribute("doctor", doctor.orElse(null));
            return "doctorDetails";
        }

    // Create Operation (HTTP POST)
    @PostMapping
    public ResponseEntity<Users> createDoctor(@RequestBody Users doctor) {
        doctor.setRole(Users.Role.doctor);
        Users savedDoctor = userRepo.save(doctor);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDoctor);
    }

    // Read Operation (HTTP GET)
    @GetMapping("/{id}")
    public ResponseEntity<Users> getDoctorById(@PathVariable Long id) {
        Optional<Users> doctor = userRepo.findByIdAndRole(id, Users.Role.doctor);
        return doctor.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Update Operation (HTTP PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Users> updateDoctor(@PathVariable Long id, @RequestBody Users doctor) {
        Optional<Users> existingDoctor = userRepo.findByIdAndRole(id, Users.Role.doctor);
        if (existingDoctor.isPresent()) {
            doctor.setId(id);
            doctor.setRole(Users.Role.doctor);
            Users updatedDoctor = userRepo.save(doctor);
            return ResponseEntity.ok().body(updatedDoctor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete Operation (HTTP DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteDoctor(@PathVariable Long id) {
        Optional<Users> existingDoctor = userRepo.findByIdAndRole(id, Users.Role.doctor);
        if (existingDoctor.isPresent()) {
            userRepo.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
