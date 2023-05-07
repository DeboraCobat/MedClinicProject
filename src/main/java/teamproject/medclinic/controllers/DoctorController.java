//package teamproject.medclinic.controllers;
//
//import java.util.List;
//import java.lang.IllegalArgumentException;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.bind.annotation.ModelAttribute;
//
//import teamproject.medclinic.entity.User;
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
//    // List ALL Operation
//    @RequestMapping("/admin/doctorsList")
//    public String doctorsList(Model model) {
//        List<User> doctors = userRepo.findByRole(User.Role.doctor);
//        model.addAttribute("doctors", doctors);
//        return "admin/doctorsList";
//    }
//
//    // Create Operation
//    @RequestMapping("/admin/doctorCreate")
//    public String doctorCreate(Model model) {
//        User user = new User();
//        user.setRole(User.Role.doctor);
//        model.addAttribute("user", user);
//        return "admin/doctorCreate";
//    }
//
//    @PostMapping("/admin/doctorCreate")
//    public String doctorCreate(@ModelAttribute("user") User user) {
//        user.setRole(User.Role.doctor);
//        userRepo.save(user);
//        return "redirect:/admin/doctorsList";
//    }
//
//    // Update Operation
//    @RequestMapping("/admin/doctorUpdate/{id}")
//    public String doctorUpdate(@PathVariable("id") Long id, Model model) {
//        User user = userRepo.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
//        model.addAttribute("user", user);
//        return "admin/doctorUpdate";
//    }
//
//    @PostMapping("/admin/doctorUpdate")
//    public String doctorUpdate(@ModelAttribute("user") User user) {
//        userRepo.save(user);
//        return "redirect:/admin/doctorsList";
//    }
//}
