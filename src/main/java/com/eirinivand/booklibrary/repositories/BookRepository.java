package com.eirinivand.booklibrary.repositories;

import com.eirinivand.booklibrary.entities.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
}
