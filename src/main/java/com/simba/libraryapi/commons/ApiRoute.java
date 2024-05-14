package com.simba.libraryapi.commons;

public interface ApiRoute {
    String BASE_URI = "/api/v1/";
    String BOOKS = BASE_URI+"books";
    String PATRONS = BASE_URI+"patrons";
    String BORROW_BOOKS = BASE_URI+"borrow-books";
}
