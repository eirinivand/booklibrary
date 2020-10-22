package com.eirinivand.booklibrary.repositories;

import com.eirinivand.booklibrary.entities.UserBookLoan;
import com.eirinivand.booklibrary.entities.UserBookLoanKey;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LoanRepository  extends CrudRepository<UserBookLoan, UserBookLoanKey> {

    @Transactional
    @Modifying
    @Query("delete from UserBookLoan l where l.book.id= ?1 and l.user.id= ?2")
    int deleteByIds(Long bookId, Long userId);
}
