package com.eirinivand.booklibrary.controllers;

import com.eirinivand.booklibrary.entities.Book;
import com.eirinivand.booklibrary.entities.User;
import com.eirinivand.booklibrary.entities.UserBookLoan;
import com.eirinivand.booklibrary.entities.UserBookLoanKey;
import com.eirinivand.booklibrary.services.BookService;
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
public class BookController {

    @Autowired
    private BookService bookService;


    @Autowired
    private LoanService loanService;


    @Autowired
    private UserService userService;

    @GetMapping("/new-book")
    public String newBookForm(Model model) {
        model.addAttribute("newBook", new Book());
        return "book/new-book";
    }

    @PostMapping("/new-book")
    public String newBookSubmit(@ModelAttribute Book newBook, Model model) {
        try {
            Book book = bookService.save(newBook);
            model.addAttribute("newBook", book);
            return "result";
        } catch (DataIntegrityViolationException ex) {
            model.addAttribute("error", "Book Already Exists");
            return "result";
        }

    }

    @GetMapping("/manage-books")
    public String manageBooks(Model model) {
        HashMap<Long, List<User>> loans = new HashMap<>();
        ArrayList<UserBookLoan> allLoans = loanService.findAll();
        for (Book b : bookService.findAll()) {
            loans.put(b.getId(), allLoans.stream().filter(l -> b.equals(l.getBook())).map(l -> l.getUser()).collect(Collectors.toList()));
        }
        model.addAttribute("books", bookService.findAll());
        model.addAttribute("loans", loans);
        return "book/manage";
    }

    @GetMapping("/loan-book")
    public String returnBook(Model model) {
        ArrayList<UserBookLoan> allLoans = loanService.findAll();
        ArrayList<User> users = userService.findAll();
        HashMap<Long, List<User>> availableLoans = new HashMap<>();
        for (Book b : bookService.findAll()) {
            availableLoans.put(b.getId(), users.stream().filter(u ->
                    !allLoans.stream().anyMatch(l -> u.equals(l.getUser()) && b.equals(l.getBook()))).collect(Collectors.toList()));
        }

        model.addAttribute("books", bookService.findAll());
        model.addAttribute("users", userService.findAll());
        model.addAttribute("availableLoans", availableLoans);
        return "loan";
    }

    @ResponseBody
    @PostMapping("/return-book")
    public String returnBookFromUser(@RequestParam Long bookId, @RequestParam Long userId) {
        try {
            Book b = bookService.findById(bookId).orElse(null);
            if (b == null) {
                throw new Exception("No such Book");
            }
            User u = userService.findById(userId).orElse(null);
            if (u == null) {
                throw new Exception("No such User");
            }

                int succ = bookService.updateAvailableCopies(b, b.getAvailableCopies() + 1);
                if (succ > 0) {
                    loanService.deleteByIds(b.getId(), u.getId());
                    return "returned";
                } else {
                    return "error updating available copies";
                }


        } catch (Exception e) {
            return e.toString();
        }
    }

    @GetMapping("/return-book")
    public String returnBookList(Model model) {
        ArrayList<UserBookLoan> allLoans = loanService.findAll();
        HashMap<Book, List<User>> availableLoans = new HashMap<>();
        for (Book b : allLoans.stream().map(l -> l.getBook()).distinct().collect(Collectors.toList())) {
            availableLoans.put(b, allLoans.stream().filter(l -> l.getBook().equals(b)).map(l -> l.getUser()).collect(Collectors.toList()));
        }
        model.addAttribute("availableLoans", availableLoans);
        return "return";
    }

    @ResponseBody
    @PostMapping("/loan-book")
    public String loanBookToUser(@RequestParam Long bookId, @RequestParam Long userId, Model model) {
        try {
            Book b = bookService.findById(bookId).orElse(null);
            if (b == null) {
                throw new Exception("No such Book");
            }
            User u = userService.findById(userId).orElse(null);
            if (u == null) {
                throw new Exception("No such User");
            }
            if (b.getAvailableCopies() > 0) {
                int succ = bookService.updateAvailableCopies(b, b.getAvailableCopies() - 1);
                if (succ > 0) {
                    loanService.save(new UserBookLoan(new UserBookLoanKey(b.getId(), u.getId()), b, u));
                }
                return "loaned";
            } else {
                return "Not enough copies";
            }
        } catch (Exception e) {
            return e.toString();
        }
    }

    @ResponseBody
    @DeleteMapping("/delete-book")
    public String deleteBookSubmit(@RequestParam Long id) {
        try {
            Book book = bookService.findById(id).orElse(null);
            if (book == null) {
                return "Error finding book";
            }

            ArrayList<User> loanedTo = new ArrayList<>();
            for (UserBookLoan user : book.getUserBookLoans()) {
                loanedTo.add(user.getUser());
            }
            return "Not deleted Book is on loan to users: " + loanedTo.toString();

        } catch (DataIntegrityViolationException ex) {
            return "Book could not be deleted";
        }

    }


}
