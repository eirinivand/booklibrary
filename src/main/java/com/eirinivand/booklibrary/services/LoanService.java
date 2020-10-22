package com.eirinivand.booklibrary.services;

import com.eirinivand.booklibrary.entities.Book;
import com.eirinivand.booklibrary.entities.User;
import com.eirinivand.booklibrary.entities.UserBookLoan;
import com.eirinivand.booklibrary.entities.UserBookLoanKey;
import com.eirinivand.booklibrary.repositories.BookRepository;
import com.eirinivand.booklibrary.repositories.LoanRepository;
import com.eirinivand.booklibrary.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    public void save(UserBookLoan loan) {
        loanRepository.save(loan);
    }

    public Optional<UserBookLoan> findById(UserBookLoanKey key) {
       return loanRepository.findById(key);
    }

    public ArrayList<UserBookLoan> findAll() {

        Iterable<UserBookLoan> it = loanRepository.findAll();

        ArrayList<UserBookLoan> users = new ArrayList<UserBookLoan>();
        it.forEach(e -> users.add(e));

        return users;
    }
    public void deleteByIds(Long bookId, Long userId) {

        loanRepository.deleteByIds(bookId,userId);
    }

}
