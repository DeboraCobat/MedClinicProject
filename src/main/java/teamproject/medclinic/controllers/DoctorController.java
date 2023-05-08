package teamproject.medclinic.controllers;

import java.util.List;
import java.lang.IllegalArgumentException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ModelAttribute;

import teamproject.medclinic.entity.Appointments;
import teamproject.medclinic.entity.User;
import teamproject.medclinic.repository.UserRepo;

@Controller
public class DoctorController {
    private final UserRepo userRepo;

    public DoctorController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    //// DOCTOR CRUD ////

    // List ALL Operation
    @GetMapping("/admin/doctorsList")
    public String doctorsList(Model model) {
        List<User> doctors = userRepo.findByRole(User.Role.doctor);
        model.addAttribute("doctors", doctors);
        return "admin/doctorsList";
    }

    // Create Operation
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

    // Update Operation
    @GetMapping("/admin/doctorUpdate/{id}")
    public String doctorUpdate(@PathVariable("id") Long id, Model model) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        return "admin/doctorUpdate";
    }

    @PutMapping("/admin/updateDoctor/{id}")
    public String updateDoctor(@PathVariable("id") Long id, @ModelAttribute("user") User user) {
        User existingDoctor = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        existingDoctor.setFirstName(user.getFirstName());
        existingDoctor.setLastName(user.getLastName());
        existingDoctor.setDateOfBirth(user.getDateOfBirth());
        existingDoctor.setAddress(user.getAddress());
        existingDoctor.setPhoneNumber(user.getPhoneNumber());
        existingDoctor.setEmail(user.getEmail());
        existingDoctor.setSpecialty(user.getSpecialty());

        userRepo.save(existingDoctor);

        return "redirect:/admin/doctorsList";
    }


    // DOCTOR Delete Operation
    @RequestMapping ("/admin/doctorDelete/{id}")
    public String doctorDelete(@PathVariable("id") Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid appointment id: " + id));
        userRepo.delete(user);
        return "redirect:/admin/doctorsList";
    }
}