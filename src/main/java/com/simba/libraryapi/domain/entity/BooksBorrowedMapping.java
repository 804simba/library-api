package com.simba.libraryapi.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Table(name = "patron_book_borrowed_mapping")
@Entity
public class BooksBorrowedMapping extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patron_id")
    private Patron patron;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "borrowed_date")
    private LocalDateTime borrowedDate;

    @Column(name = "return_date")
    private LocalDateTime returnDate;
}
