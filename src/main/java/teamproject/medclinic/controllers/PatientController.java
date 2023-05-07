//package teamproject.medclinic.controllers;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import teamproject.medclinic.entity.User;
//import teamproject.medclinic.repository.UserRepo;
//
//import java.util.List;
//
//@Controller
//public class PatientController {
//    private final UserRepo userRepo;
//
//    public PatientController(UserRepo userRepo) {
//        this.userRepo = userRepo;
//    }
//
//    // List ALL Operation (HTTP GET)
//    @GetMapping("/admin/patientsList")
//    public String patientsList(Model model) {
//        List<User> patients = userRepo.findByRole(User.Role.patient);
//        model.addAttribute("patients", patients);
//        return "admin/patientsList";
//    }
//
//    // Create Operation (HTTP Post)
//    @RequestMapping("/admin/patientCreate")
//    public String patientCreate(Model model) {
//        User user = new User();
//        user.setRole(User.Role.patient);
//        model.addAttribute("user", user);
//        return "admin/patientCreate";
//    }
//
//    @PostMapping("/admin/patientCreate")
//    public String patientCreate(@ModelAttribute("user") User user) {
//        user.setRole(User.Role.patient);
//        userRepo.save(user);
//        return "redirect:/admin/patientsList";
//    }
//}