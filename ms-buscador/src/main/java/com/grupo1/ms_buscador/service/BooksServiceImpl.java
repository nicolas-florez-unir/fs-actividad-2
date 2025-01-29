package com.grupo1.ms_buscador.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import com.grupo1.ms_buscador.data.entity.BookEntity;
import com.grupo1.ms_buscador.data.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;


@Service
@Slf4j
public class BooksServiceImpl implements BooksService{

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<BookEntity> getBooks(String title, String author, String description, Boolean visible, String category, String publicationDate, int rating, Double price, Integer unitsAvaible) {

        if (StringUtils.hasLength(title) || StringUtils.hasLength(author) || StringUtils.hasLength(description)
                || visible != null || StringUtils.hasLength(category) || StringUtils.hasLength(publicationDate) || rating != 0) {
            return bookRepository.search(title, author, description, visible, category, publicationDate, rating, price, unitsAvaible);
        }

        List<BookEntity> books = bookRepository.getBooks();
        return books.isEmpty() ? null : books;
    }


}
