package com.eirinivand.booklibrary.services;

import com.eirinivand.booklibrary.entities.Book;
import com.eirinivand.booklibrary.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> findAll() {

        Iterable<Book> it = bookRepository.findAll();

        ArrayList<Book> books = new ArrayList<Book>();
        it.forEach(e -> books.add(e));

        return books;
    }

    public Book save(Book book) {
        book.setAvailableCopies(book.getTotalCopies());

        Book newBook = bookRepository.save(book);

        return newBook;
    }

    public Long count() {
        return bookRepository.count();
    }

    public void deleteById(Long bookId) {

        bookRepository.deleteById(bookId);
    }
}
