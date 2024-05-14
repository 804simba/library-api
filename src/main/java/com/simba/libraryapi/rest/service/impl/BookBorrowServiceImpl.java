package com.simba.libraryapi.rest.service.impl;

import com.simba.libraryapi.commons.ResponseCode;
import com.simba.libraryapi.domain.dto.base.BaseResponse;
import com.simba.libraryapi.domain.dto.books.BorrowBookResponse;
import com.simba.libraryapi.domain.entity.Book;
import com.simba.libraryapi.domain.entity.Patron;
import com.simba.libraryapi.domain.entity.BooksBorrowedMapping;
import com.simba.libraryapi.domain.repository.BookRepository;
import com.simba.libraryapi.domain.repository.BooksBorrowedMappingRepository;
import com.simba.libraryapi.domain.repository.PatronRepository;
import com.simba.libraryapi.rest.service.BookBorrowingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookBorrowServiceImpl implements BookBorrowingService {

    private final BookRepository bookRepository;

    private final PatronRepository patronRepository;

    private final BooksBorrowedMappingRepository bookBorrowedMappingRepository;

    @Override
    public BaseResponse borrowBook(Long bookId, Long patronId) {
       BaseResponse baseResponse;
       try {
           Optional<Book> bookOptional = bookRepository.findById(bookId);
           Optional<Patron> patronOptional = patronRepository.findById(patronId);
           if (bookOptional.isPresent() && patronOptional.isPresent()) {
               Book book = bookOptional.get();
               Patron patron = patronOptional.get();
               if (!book.isBorrowed()) {
                   BooksBorrowedMapping bookBorrowedMapping = new BooksBorrowedMapping();
                   bookBorrowedMapping.setBook(book);
                   bookBorrowedMapping.setPatron(patron);
                   bookBorrowedMapping.setBorrowedDate(LocalDateTime.now());
                   bookBorrowedMapping.setReturnDate(LocalDateTime.now().plusDays(10L));
                   bookBorrowedMappingRepository.save(bookBorrowedMapping);

                   BorrowBookResponse.BookData bookData = new BorrowBookResponse.BookData();
                   bookData.setName(book.getTitle());
                   baseResponse = new BaseResponse(ResponseCode.OK, "Book borrowed successfully", bookData);
               } else {
                   baseResponse = new BaseResponse(ResponseCode.OK, "Book is already borrowed, check back later!");
               }
           } else {
               baseResponse = new BaseResponse(ResponseCode.NOT_FOUND, "Book not found");
           }
       } catch (Exception e) {
           baseResponse = new BaseResponse(ResponseCode.INTERNAL_ERROR, "Something went wrong, please try again");
       }
        return baseResponse;
    }

    @Override
    public BaseResponse returnBook(Long bookId, Long patronId) {
        BaseResponse baseResponse = null;
        try {
            Optional<Book> bookOptional = bookRepository.findById(bookId);
            Optional<Patron> patronOptional = patronRepository.findById(patronId);
            if (bookOptional.isPresent() && patronOptional.isPresent()) {
                Book book = bookOptional.get();
                Patron patron = patronOptional.get();

                BooksBorrowedMapping bookBorrowedMapping = bookBorrowedMappingRepository.findBooksBorrowedMappingByBookAndPatron(book, patron);
                if (bookBorrowedMapping != null) {
                    bookBorrowedMappingRepository.delete(bookBorrowedMapping);
                    book.setBorrowed(false);
                    bookRepository.save(book);

                    return new BaseResponse(ResponseCode.OK, "Book returned successfully");
                } else {
                    return new BaseResponse(ResponseCode.NOT_FOUND, "Book is not borrowed by this patron");
                }
        }
        } catch (Exception e) {
            baseResponse = new BaseResponse(ResponseCode.INTERNAL_ERROR, "Something went wrong, please try again");
        }
        return baseResponse;
    }
}
