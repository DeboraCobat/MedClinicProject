package teamproject.medclinic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

}