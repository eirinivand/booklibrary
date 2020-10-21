package com.eirinivand.booklibrary.controllers;

import com.eirinivand.booklibrary.entities.Book;
import com.eirinivand.booklibrary.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/new-book")
    public String newBookForm(Model model) {
        model.addAttribute("newBook", new Book());
        return "book/new-book";
    }

    @GetMapping("/manage-books")
    public String manageBooks(Model model) {
        return "book/manage";
    }

    @GetMapping("/return-book")
    public String returnBook(Model model) {
        return "book/return";
    }

    @GetMapping("/loan-book")
    public String loanBook(Model model) {
        return "book/loan";
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
}
