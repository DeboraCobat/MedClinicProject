package teamproject.medclinic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import teamproject.medclinic.entity.User;
import teamproject.medclinic.repository.UserRepo;

import java.util.List;

@Controller
@RequestMapping("/admin")
//@PreAuthorize("hasRole('admin')")
public class AdminController {

    private final UserRepo userRepo;

    public AdminController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping ("/dashboard")
    public String dashboard(Model model) {
        List<User> admin = userRepo.findAll();
        model.addAttribute("admin", admin);
        return "admin/dashboard";
    }

    @GetMapping ("/doctorsList")
    public String doctorsList(Model model) {
        List<User> doctors = userRepo.findByRole(User.Role.doctor);
        model.addAttribute("doctors", doctors);
        return "admin/doctorsList";
    }

    // Create Operation
    @GetMapping ("/doctorCreate")
    public String doctorCreateForm(Model model) {
        User user = new User();
        user.setRole(User.Role.doctor);
        model.addAttribute("user", user);
        return "admin/doctorCreate";
    }


    @PostMapping("/doctorCreate")
    public String doctorCreate(@ModelAttribute("user") User user) {
        user.setRole(User.Role.doctor);
        userRepo.save(user);
        return "redirect:/admin/doctorsList";
    }

    // Update Operation
    @GetMapping("/doctorUpdate/{id}")
    public String doctorUpdate(@PathVariable("id") Long id, Model model) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        return "admin/doctorUpdate";
    }

    @PostMapping("/doctorUpdate/{id}")
    public String doctorUpdate(@PathVariable("id") Long id, @ModelAttribute("user") User updatedUser) {
        User existingUser = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setDateOfBirth(updatedUser.getDateOfBirth());
        existingUser.setAddress(updatedUser.getAddress());
        existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setSpecialty(updatedUser.getSpecialty());
        userRepo.save(existingUser);
        return "redirect:/admin/doctorsList";
    }


}