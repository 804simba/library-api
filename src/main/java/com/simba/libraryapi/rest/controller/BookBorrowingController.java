package com.simba.libraryapi.rest.controller;

import com.simba.libraryapi.commons.ApiRoute;
import com.simba.libraryapi.domain.dto.base.BaseResponse;
import com.simba.libraryapi.rest.service.BookBorrowingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = ApiRoute.BORROW_BOOKS, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class BookBorrowingController {

    private final BookBorrowingService bookBorrowingService;

    @PostMapping(value = "/{bookId}/patron/{patronId}")
    public BaseResponse borrowBook(@PathVariable @Valid Long bookId, @PathVariable @Valid Long patronId) {
        return bookBorrowingService.borrowBook(bookId, patronId);
    }

    @PutMapping(value = "/return/{bookId}/patron/{patronId}")
    public BaseResponse returnBook(@PathVariable @Valid Long bookId, @PathVariable @Valid Long patronId) {
        return bookBorrowingService.returnBook(bookId, patronId);
    }
}
