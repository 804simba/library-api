package com.simba.libraryapi.rest.service;

import com.simba.libraryapi.domain.dto.base.BaseResponse;

public interface BookBorrowingService {
    BaseResponse borrowBook(Long bookId, Long patronId);
    BaseResponse returnBook(Long bookId, Long patronId);
}
