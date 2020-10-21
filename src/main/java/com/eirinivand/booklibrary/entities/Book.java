package com.eirinivand.booklibrary.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
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

    @Column(nullable = false, name = "available_copies")
    private Integer availableCopies;

    @OneToMany(mappedBy = "user")
    private Set<UserBookLoan> userBookLoans;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) &&
                Objects.equals(name, book.name) &&
                Objects.equals(summary, book.summary) &&
                Objects.equals(isbn, book.isbn) &&
                Objects.equals(totalCopies, book.totalCopies) &&
                Objects.equals(availableCopies, book.availableCopies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, summary, isbn, totalCopies, availableCopies);
    }
}
