package com.eirinivand.booklibrary.controllers;

import com.eirinivand.booklibrary.entities.User;
import com.eirinivand.booklibrary.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(name = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/new-user")
    public String newUserForm(Model model) {
        model.addAttribute("newUser", new User());
        return "user/new-user";
    }
    @GetMapping("/manage-users")
    public String manage(Model model) {
        model.addAttribute("newUser", new User());
        return "user/manage";
    }
    @PostMapping("/new-user")
    public String newUserSubmit(@ModelAttribute User newUser, Model model) {
        try {
            User user = userService.save(newUser);
            model.addAttribute("newUser", user);
            return "result";
        } catch (DataIntegrityViolationException ex) {
            model.addAttribute("error", "User Already Exists");
            return "result";
        }

    }
}
