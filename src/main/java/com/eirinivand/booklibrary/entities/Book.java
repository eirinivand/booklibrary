package com.eirinivand.booklibrary.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Setter
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Book implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String summary;

    @Column(nullable = false, unique = true)
    private String isbn;

    @Column(nullable = false, name = "total_copies")
    private Integer totalCopies;

    @Column(nullable = false, name = "available_copies", updatable = true)
    private Integer availableCopies;

    @OneToMany(mappedBy = "user")
    private Set<UserBookLoan> userBookLoans;

}
