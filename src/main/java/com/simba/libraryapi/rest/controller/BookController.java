package com.simba.libraryapi.rest.controller;

import com.simba.libraryapi.commons.ApiRoute;
import com.simba.libraryapi.domain.dto.base.BaseResponse;
import com.simba.libraryapi.domain.dto.books.SaveOrUpdateBookRequest;
import com.simba.libraryapi.rest.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = ApiRoute.BOOKS, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class BookController {

    private final BookService bookService;

    @PostMapping(value = "/save")
    public BaseResponse save(@RequestBody SaveOrUpdateBookRequest saveOrUpdateBookRequest) {
        return bookService.saveOrUpdateBook(saveOrUpdateBookRequest);
    }

    @PutMapping(value = "/update")
    public BaseResponse update(@RequestBody SaveOrUpdateBookRequest saveOrUpdateBookRequest) {
        return bookService.saveOrUpdateBook(saveOrUpdateBookRequest);
    }

    @GetMapping(value = "/{bookId}")
    public BaseResponse getBook(@PathVariable Long bookId) {
        return bookService.getBookById(bookId);
    }

    @GetMapping
    public BaseResponse getBooks(@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                                 @RequestParam(name = "pageSize", defaultValue = "30") int pageSize) {
        return bookService.getAllBooks(pageSize, pageNumber);
    }

    @DeleteMapping(value = "/delete/{bookId}")
    public BaseResponse delete(@PathVariable Long bookId) {
        return bookService.deleteBook(bookId);
    }
}
