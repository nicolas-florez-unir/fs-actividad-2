package com.grupo1.ms_buscador.service;

import com.grupo1.ms_buscador.controllers.model.*;
import com.grupo1.ms_buscador.data.entity.BookEntity;

import java.util.List;

public interface BooksService {
    List<BookEntity> getBooks(String title, String author, String description, Boolean visible, String category, String publicationDate, int rating);
    BookEntity getBook(String bookId);
    Boolean removeBook(String bookId);
    BookEntity createBook(CreateBookRequest request);
    BookEntity updateBook(String bookId, String updateRequest);
    BookEntity updateBook(String bookId, BookDto updateRequest);

}
