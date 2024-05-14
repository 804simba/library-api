package com.simba.libraryapi.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(
        name = "books",
        uniqueConstraints = { @UniqueConstraint(name = "booksUniqueConstraint", columnNames = {"book_title", "book_author"})},
        indexes = {@Index(name = "patronsIndex", columnList = "books_title")}
)
@Entity
public class Book extends BaseEntity {

    @Column(name = "book_title")
    private String title;

    @Column(name = "book_author")
    private String author;

    @Column(name = "book_isbn")
    private String isbn;

    @Column(name = "book_publication_year")
    private String publicationYear;

    @Column(name = "is_book_borrowed")
    private boolean isBorrowed = false;

    @Override
    public int hashCode() {
        return (this.getId() != null ? this.getId().hashCode() : 0);
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Book) && ((Book) obj).getId() != null;
    }
}
