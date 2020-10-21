package com.eirinivand.booklibrary.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UserBookLoan {

    @EmbeddedId
    UserBookLoanKey id;

    @ManyToOne
    @MapsId("bookId")
    @JoinColumn(name = "book_id")
    Book book;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    User user;

}
