package com.grupo1.ms_buscador.books.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.grupo1.ms_buscador.books.domain.dtos.BookDto;
import com.grupo1.ms_buscador.books.domain.repositories.BookRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class UpdateBookUseCase {

    @Autowired
    private BookRepository repository;

    public BookDto execute(Long bookId, BookDto bookDto) {

        return repository.update(bookDto);
    }
}