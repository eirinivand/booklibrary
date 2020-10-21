package com.eirinivand.booklibrary.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Setter
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UserBookLoanKey implements Serializable {

    @Column(name = "user_id")
    Long userId;

    @Column(name = "book_id")
    Long bookId;

}
