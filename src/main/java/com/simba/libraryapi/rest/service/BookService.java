package com.simba.libraryapi.rest.service;

import com.simba.libraryapi.domain.dto.base.BaseResponse;
import com.simba.libraryapi.domain.dto.books.SaveOrUpdateBookRequest;

public interface BookService {
    BaseResponse saveOrUpdateBook(SaveOrUpdateBookRequest saveOrUpdateBookRequest);

    BaseResponse getBookById(Long bookId);

    BaseResponse getAllBooks(int pageSize, int pageNumber);

    BaseResponse deleteBook(Long bookId);
}
