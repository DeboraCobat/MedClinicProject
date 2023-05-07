package teamproject.medclinic.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import teamproject.medclinic.entity.Appointments;
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


    //// DOCTOR CRUD ////

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

    @PostMapping("/updateDoctor/{id}")
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

        System.out.println(user);
        System.out.println(existingDoctor);

        userRepo.save(existingDoctor);

        return "redirect:/admin/doctorsList";
    }

//    @PutMapping("/updateDoctor/{id}")
//    public ResponseEntity<User> updateDoctor(@PathVariable(value = "id") Long id,
//                                             @Valid @ModelAttribute("user") User user){
//        User existingDoctor = userRepo.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
//        existingDoctor.setFirstName(user.getFirstName());
//        existingDoctor.setLastName(user.getLastName());
//        existingDoctor.setDateOfBirth(user.getDateOfBirth());
//        existingDoctor.setAddress(user.getAddress());
//        existingDoctor.setPhoneNumber(user.getPhoneNumber());
//        existingDoctor.setEmail(user.getEmail());
//        existingDoctor.setSpecialty(user.getSpecialty());
//
//        final User updatedUser = userRepo.save(existingDoctor);
//        return ResponseEntity.ok(updatedUser);
//    }

    //Delete Operation
    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userRepo.deleteById(id);
        return "redirect:/admin/doctorsList";
    }


    //// PATIENT CRUD ////

    // List ALL Operation
    @GetMapping("/patientsList")
    public String patientsList(Model model) {
        List<User> patients = userRepo.findByRole(User.Role.patient);
        model.addAttribute("patients", patients);
        return "admin/patientsList";
    }

    // Create Operation
    @GetMapping("/patientCreate")
    public String patientCreate(Model model) {
        User user = new User();
        user.setRole(User.Role.patient);
        model.addAttribute("user", user);
        return "admin/patientCreate";
    }

    @PostMapping("/patientCreate")
    public String patientCreate(@ModelAttribute("user") User user) {
        user.setRole(User.Role.patient);
        userRepo.save(user);
        return "redirect:/admin/patientsList";
    }

    // Update Operation
    @GetMapping("/patientUpdate/{id}")
    public String patientUpdate(@PathVariable("id") Long id, Model model) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        return "admin/patientUpdate";
    }

    @PostMapping("/patientUpdate/{id}")
    public String patientUpdate(@PathVariable("id") Long id, @ModelAttribute("user") User updatedUser) {
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

    //Delete Operation
//    @GetMapping("/deleteUser/{id}")
//    public String deleteUser(@PathVariable("id") Long id) {
//        userRepo.deleteById(id);
//        return "redirect:/admin/patientsList";
//    }


    ///APPOINTMENTS CRUD////

//    @GetMapping("/appointmentsList")
//    public String appointmentsList(Model model) {
//        List<Appointments> appointments = appointmentRepo.findAll();
//        model.addAttribute("appointments", appointments);
//        return "admin/appointmentsList";
//    }
}

