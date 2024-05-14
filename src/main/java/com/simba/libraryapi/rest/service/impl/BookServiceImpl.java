package com.simba.libraryapi.rest.service.impl;

import com.simba.libraryapi.commons.utils.PaginationPayloadUtility;
import com.simba.libraryapi.commons.ResponseCode;
import com.simba.libraryapi.commons.utils.ResponsePayloadUtility;
import com.simba.libraryapi.domain.dto.base.BaseResponse;
import com.simba.libraryapi.domain.dto.books.GetBookInformationResponse;
import com.simba.libraryapi.domain.dto.books.SaveOrUpdateBookRequest;
import com.simba.libraryapi.domain.dto.pagination.PagedResponse;
import com.simba.libraryapi.domain.entity.Book;
import com.simba.libraryapi.domain.repository.BookRepository;
import com.simba.libraryapi.rest.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    @Transactional
    public BaseResponse saveOrUpdateBook(SaveOrUpdateBookRequest saveOrUpdateBookRequest) {
        BaseResponse baseResponse = new BaseResponse();
        try {
            Book book;
            if (ObjectUtils.isEmpty(saveOrUpdateBookRequest.getBookId())) {
                book = new Book();
                createOrUpdateBookInformation(book, saveOrUpdateBookRequest);
                bookRepository.save(book);
                baseResponse.setCode(ResponseCode.CREATED);
                baseResponse.setMessage("Operation successful");
            } else {
                book = bookRepository.findBookById(saveOrUpdateBookRequest.getBookId());
                if (!ObjectUtils.isEmpty(book)) {
                    createOrUpdateBookInformation(book, saveOrUpdateBookRequest);
                } else  {
                    baseResponse.setCode(ResponseCode.NOT_FOUND);
                    baseResponse.setMessage("Book not found");
                }
            }
        } catch (Exception e) {
            baseResponse.setCode(ResponseCode.BAD_REQUEST);
            baseResponse.setMessage("Something went wrong, please try again");
        }
        return baseResponse;
    }

    private void createOrUpdateBookInformation(Book book, SaveOrUpdateBookRequest saveOrUpdateBookRequest) {
        book.setTitle(saveOrUpdateBookRequest.getTitle());
        book.setAuthor(saveOrUpdateBookRequest.getAuthor());
        book.setIsbn(saveOrUpdateBookRequest.getIsbn());
        book.setPublicationYear(saveOrUpdateBookRequest.getPublicationYear());
    }

    @Override
    @Transactional(readOnly = true)
    public BaseResponse getBookById(Long bookId) {
        BaseResponse baseResponse = new BaseResponse();
        try {
            Book book = bookRepository.findBookById(bookId);
            if (ObjectUtils.isEmpty(book)) {
                baseResponse.setCode(ResponseCode.NOT_FOUND);
                baseResponse.setMessage("Book not found for bookId: " + bookId);
            } else {
                GetBookInformationResponse getBookInformationResponse = new GetBookInformationResponse();
                getBookInformationResponse.setTitle(book.getTitle());
                getBookInformationResponse.setAuthor(book.getAuthor());
                getBookInformationResponse.setIsbn(book.getIsbn());
                getBookInformationResponse.setPublicationYear(book.getPublicationYear());

                baseResponse.setCode(ResponseCode.OK);
                baseResponse.setMessage("Book found for bookId: " + bookId);
                baseResponse.setPayload(getBookInformationResponse);
            }
        } catch (Exception e) {
            baseResponse.setCode(ResponseCode.BAD_REQUEST);
            baseResponse.setMessage("Something went wrong, please try again");
        }
        return baseResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public BaseResponse getAllBooks(int pageSize, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Book> bookPages = bookRepository.findAll(pageable);
        if (bookPages.isEmpty()) {
            return new BaseResponse(ResponseCode.NOT_FOUND, "No available books were found");
        }
        Page<GetBookInformationResponse> pagedAvailableDrones = bookPages.map(eachBook -> {
            GetBookInformationResponse book = new GetBookInformationResponse();
            book.setTitle(eachBook.getTitle());
            book.setAuthor(eachBook.getAuthor());
            book.setIsbn(eachBook.getIsbn());
            book.setPublicationYear(eachBook.getPublicationYear());
            return book;
        });
        PagedResponse<GetBookInformationResponse> pagedResponse = PaginationPayloadUtility.resolvePaginationMetaData(pagedAvailableDrones, pageNumber, pageSize, "Successful", ResponseCode.OK, "Fetched available books");
        return ResponsePayloadUtility.createSuccessResponse(pagedResponse,"Operation completed successfully");
    }

    @Override
    public BaseResponse deleteBook(Long bookId) {
        bookRepository.deleteById(bookId);
        return new BaseResponse(ResponseCode.OK, "Book deleted successfully");
    }
}
