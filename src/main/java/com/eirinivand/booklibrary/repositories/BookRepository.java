package com.eirinivand.booklibrary.repositories;

import com.eirinivand.booklibrary.entities.Book;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    @Transactional
    @Modifying
    @Query("update Book b set b.availableCopies = ?1 where b.id = ?2")
    int setAvailableCopiesForBookId(Integer status, Long id);
}
