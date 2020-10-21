package com.eirinivand.booklibrary.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

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

    @Column(nullable = false)
    private Integer isbn;

    @Column(nullable = false, name = "total_copies")
    private Integer totalCopies;

    @Column(nullable = false, name = "available_copies")
    private Integer availableCopies;


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
