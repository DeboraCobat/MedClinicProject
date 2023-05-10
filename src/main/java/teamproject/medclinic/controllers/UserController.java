package teamproject.medclinic.controllers;

import ch.qos.logback.core.LayoutBase;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import teamproject.medclinic.entity.User;
import teamproject.medclinic.repository.UserRepo;
import org.springframework.ui.Model;

import java.security.Principal;


@Controller
public class UserController {

    @Autowired
    private UserRepo userRepo;

//    @GetMapping({"/", "/home"})
//    public String showHome(Model model, HttpSession session) {
//        User user = (User) session.getAttribute("user");
//        model.addAttribute("user", user);
//        return "home";
//    }

    @GetMapping({"/", "/home"})
    public String showHome(Model model, HttpSession session, Principal principal) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);

        if (principal != null) {
            String loggedInAs = "User: " + principal.getName();
            model.addAttribute("loggedInAs", loggedInAs);
        } else {
            model.addAttribute("loggedInAs", "Register/Sign in");
        }

        return "home";
    }

    @GetMapping("/contact")
    public String contactUs(Model model) {
        model.addAttribute("message", "Contact us");
        return "contact";
    }

    @GetMapping("/register")
    public ModelAndView register() {
        ModelAndView mav = new ModelAndView("registration");
        User newUser = new User();
        mav.addObject("user", newUser);
        return mav;
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user) {
        user.setRole(User.Role.patient);
        userRepo.save(user);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        HttpSession session,
                        Model model) {
        if (email == null || password == null) {
            model.addAttribute("errorLogin", "Please provide both email and password");
            return "login";
        }

        User user = userRepo.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("user", user);
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/profile")
    public String showProfile(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "You are not logged in");
            return "login";
        }
        model.addAttribute("user", user);
        return "profile";
    }

}
