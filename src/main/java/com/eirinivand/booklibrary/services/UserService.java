package com.eirinivand.booklibrary.services;

import com.eirinivand.booklibrary.entities.User;
import com.eirinivand.booklibrary.repositories.UserRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {

        Iterable<User> it = userRepository.findAll();

        ArrayList<User> users = new ArrayList<User>();
        it.forEach(e -> users.add(e));

        return users;
    }

    public User save(User user) {
        user.setLoanedBooks(new HashSet<>());

        User newUser = userRepository.save(user);

        return newUser;
    }

    public Long count() {
        return userRepository.count();
    }

    public void deleteById(Long userId) {

        userRepository.deleteById(userId);
    }
}
