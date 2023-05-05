package teamproject.medclinic.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import teamproject.medclinic.entity.Users;
import teamproject.medclinic.repository.UserRepo;

import java.util.List;

@Controller
public class PatientController {
    private final UserRepo userRepo;

    public PatientController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    // List ALL Operation (HTTP GET)
    @GetMapping("/admin/patientsList")
    public String patientsList(Model model) {
        List<Users> patients = userRepo.findByRole(Users.Role.patient);
        model.addAttribute("patients", patients);
        return "admin/patientsList";
    }


    // Create Operatio (HTTP Post)
    @RequestMapping(value = "/admin/patientCreate", method = RequestMethod.GET)
    public String patientCreate() {
        return "admin/patientCreate";
    }

    @PostMapping("/admin/patientCreate")
    public ResponseEntity<Users> patientCreate(@RequestBody Users patient) {
        patient.setRole(Users.Role.patient);
        Users savedPatient = userRepo.save(patient);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPatient);
    }
}
