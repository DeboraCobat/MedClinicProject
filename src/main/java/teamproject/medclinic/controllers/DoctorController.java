package teamproject.medclinic.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import teamproject.medclinic.entity.Users;
import teamproject.medclinic.repository.UserRepo;

@Controller
public class DoctorController {
    private final UserRepo userRepo;

    public DoctorController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/doctorsList")
    public String doctorsList(Model model) {
        List<Users> doctors = userRepo.findByRole(Users.Role.doctor);
        model.addAttribute("doctors", doctors);
        return "doctorsList";
    }

}