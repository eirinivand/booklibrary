package com.eirinivand.booklibrary.repositories;

import com.eirinivand.booklibrary.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
