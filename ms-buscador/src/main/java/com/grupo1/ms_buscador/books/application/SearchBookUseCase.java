package com.grupo1.ms_buscador.books.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.grupo1.ms_buscador.books.domain.dtos.BookDto;
import com.grupo1.ms_buscador.books.domain.dtos.SearchBookFiltersDto;
import com.grupo1.ms_buscador.books.domain.repositories.BookRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class SearchBookUseCase {

    @Autowired
    private BookRepository repository;

    public List<BookDto> execute(SearchBookFiltersDto filters) {
        return repository.search(filters);
    }

}
