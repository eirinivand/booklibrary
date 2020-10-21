package com.eirinivand.booklibrary.controllers;

import com.eirinivand.booklibrary.entities.Book;
import com.eirinivand.booklibrary.entities.User;
import com.eirinivand.booklibrary.entities.UserBookLoan;
import com.eirinivand.booklibrary.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

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

    @GetMapping("/manage-users")
    public String manage(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user/manage";
    }

    @ResponseBody
    @DeleteMapping("/delete-user")
    public String deletUserSubmit(@RequestParam Long id) {
        try {
            User user = userService.findById(id).orElse(null);
            if (user == null) {
                return "Error finding book";
            }
            if (user.getLoanedBooks() == null || (user.getLoanedBooks() != null && user.getLoanedBooks().size() == 0)) {
                userService.deleteById(user.getId());
                return "" + user.getFirstName()+ " " + user.getLastName();
            } else {
                ArrayList<Book> loanedTo = new ArrayList<>();
                for (UserBookLoan userbl : user.getLoanedBooks()) {
                    loanedTo.add(userbl.getBook());
                }
                return "Not deleted Book is on loan to users: " + loanedTo.toString();
            }
        } catch (DataIntegrityViolationException ex) {
            return "Book could not be deleted";
        }

    }
}
