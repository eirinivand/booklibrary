package com.eirinivand.booklibrary.controllers;

import com.eirinivand.booklibrary.entities.Book;
import com.eirinivand.booklibrary.entities.User;
import com.eirinivand.booklibrary.entities.UserBookLoan;
import com.eirinivand.booklibrary.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

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
        model.addAttribute("books", bookService.findAll());
        return "book/manage";
    }

    @ResponseBody
    @DeleteMapping("/delete-book")
    public String deleteBookSubmit(@RequestParam Long id) {
        try {
            Book book = bookService.findById(id).orElse(null);
            if (book == null) {
                return "Error finding book";
            }
            if (book.getUserBookLoans() == null || (book.getUserBookLoans() != null && book.getUserBookLoans().size() == 0)) {
                bookService.deleteById(book.getId());
                return "Book: " + book.getName() + ", ISBN: " + book.getIsbn();
            } else {
                ArrayList<User> loanedTo = new ArrayList<>();
                for (UserBookLoan user : book.getUserBookLoans()) {
                    loanedTo.add(user.getUser());
                }
                return "Not deleted Book is on loan to users: " + loanedTo.toString();
            }
        } catch (DataIntegrityViolationException ex) {
            return "Book could not be deleted";
        }

    }

    @GetMapping("/return-book")
    public String returnBook(Model model) {
        return "book/return";
    }

    @GetMapping("/loan-book")
    public String loanBook(Model model) {
        return "book/loan";
    }


}
