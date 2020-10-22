package com.eirinivand.booklibrary.controllers;

import com.eirinivand.booklibrary.entities.Book;
import com.eirinivand.booklibrary.entities.User;
import com.eirinivand.booklibrary.entities.UserBookLoan;
import com.eirinivand.booklibrary.services.LoanService;
import com.eirinivand.booklibrary.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(name = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoanService loanService;

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
        HashMap<Long, List<Book>> loans = new HashMap<>();
        ArrayList<UserBookLoan> allLoans = loanService.findAll();
        for (User b : userService.findAll()) {
            loans.put(b.getId(), allLoans.stream().filter(l -> b.equals(l.getUser())).map(l -> l.getBook()).collect(Collectors.toList()));
        }
        model.addAttribute("users", userService.findAll());
        model.addAttribute("loans", loans);
        return "user/manage";
    }

    @ResponseBody
    @DeleteMapping("/delete-user")
    public String deletUserSubmit(@RequestParam Long id) {
        try {
            User user = userService.findById(id).orElse(null);
            if (user == null) {
                return "Error finding user";
            }
            if (user.getLoanedBooks() == null || (user.getLoanedBooks() != null && user.getLoanedBooks().size() == 0)) {
                userService.deleteById(user.getId());
                return "deleted";
            } else {
                ArrayList<Book> loanedTo = new ArrayList<>();
                for (UserBookLoan userbl : user.getLoanedBooks()) {
                    loanedTo.add(userbl.getBook());
                }
                return "Not deleted user has loaned: " + loanedTo.toString();
            }
        } catch (DataIntegrityViolationException ex) {
            return "User could not be deleted";
        }

    }
}
