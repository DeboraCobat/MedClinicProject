package teamproject.medclinic.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import teamproject.medclinic.repository.UserRepo;

@Controller
public class UserController {

//    @Autowired
    private UserRepo uRepo;

    @GetMapping({"/" , "/home"})
    public ModelAndView showHome() {
        ModelAndView mav = new ModelAndView("home");
        return mav;
    }

    @GetMapping ({"/register"})
    public ModelAndView register(){
        ModelAndView mav = new ModelAndView("registration");
        return mav;
    }
}
