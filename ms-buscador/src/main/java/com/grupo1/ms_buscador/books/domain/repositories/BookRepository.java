package com.grupo1.ms_buscador.books.domain.repositories;

import java.util.List;

import com.grupo1.ms_buscador.books.domain.dtos.BookDto;
import com.grupo1.ms_buscador.books.domain.dtos.SearchBookFiltersDto;

public abstract class BookRepository {
    public abstract BookDto save(BookDto bookDto);
    public abstract BookDto findById(Long id);
    public abstract void deleteById(Long id);
    public abstract List<BookDto> findAll();
    public abstract List<BookDto> search(SearchBookFiltersDto filters);
    public abstract BookDto update(BookDto bookDto);
}
