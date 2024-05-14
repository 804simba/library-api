package com.simba.libraryapi.domain.repository;

import com.simba.libraryapi.domain.entity.Book;
import com.simba.libraryapi.domain.entity.BooksBorrowedMapping;
import com.simba.libraryapi.domain.entity.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksBorrowedMappingRepository extends JpaRepository<BooksBorrowedMapping, Long> {
    BooksBorrowedMapping findBooksBorrowedMappingByBookAndPatron(Book book, Patron patron);
}
