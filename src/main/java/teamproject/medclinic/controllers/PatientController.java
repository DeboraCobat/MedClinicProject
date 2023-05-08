package teamproject.medclinic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import teamproject.medclinic.entity.User;
import teamproject.medclinic.repository.UserRepo;

import java.util.List;

@Controller
public class PatientController {
    private final UserRepo userRepo;

    public PatientController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    ////// PATIENT CRUD ////

    // List ALL Operation (HTTP GET)
    @GetMapping("/admin/patientsList")
    public String patientsList(Model model) {
        List<User> patients = userRepo.findByRole(User.Role.patient);
        model.addAttribute("patients", patients);
        return "admin/patientsList";
    }

    // Create Operation (HTTP Post)
    @RequestMapping("/admin/patientCreate")
    public String patientCreate(Model model) {
        User user = new User();
        user.setRole(User.Role.patient);
        model.addAttribute("user", user);
        return "admin/patientCreate";
    }

    @PostMapping("/admin/patientCreate")
    public String patientCreate(@ModelAttribute("user") User user) {
        user.setRole(User.Role.patient);
        userRepo.save(user);
        return "redirect:/admin/patientsList";
    }

    //PATIENT Update Operation
    @GetMapping("/admin/patientUpdate/{id}")
    public String patientUpdate(@PathVariable("id") Long id, ModelMap model) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        return "admin/patientUpdate";
    }

    @PutMapping("/admin/updatePatient/{id}")
    public String updatePatient(@PathVariable("id") Long id, @ModelAttribute("user") User updatedUser) {
        User existingUser = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setDateOfBirth(updatedUser.getDateOfBirth());
        existingUser.setAddress(updatedUser.getAddress());
        existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
        existingUser.setEmail(updatedUser.getEmail());
        userRepo.save(existingUser);
        return "redirect:/admin/patientsList";
    }

    // PATIENT Delete Operation
    @GetMapping("/admin/patientDelete{id}")
    public String patientDelete(@PathVariable("id") Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid appointment id: " + id));
        userRepo.delete(user);
        return "redirect:/admin/patientsList";
    }
}


