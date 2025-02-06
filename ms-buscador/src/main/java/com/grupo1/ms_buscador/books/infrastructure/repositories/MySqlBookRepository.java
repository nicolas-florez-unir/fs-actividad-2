package com.grupo1.ms_buscador.books.infrastructure.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.grupo1.ms_buscador.books.domain.dtos.BookDto;
import com.grupo1.ms_buscador.books.domain.dtos.SearchBookFiltersDto;
import com.grupo1.ms_buscador.books.domain.exceptions.BookNotFoundException;
import com.grupo1.ms_buscador.books.domain.repositories.BookRepository;
import com.grupo1.ms_buscador.books.infrastructure.entities.BookEntity;
import com.grupo1.ms_buscador.books.infrastructure.specifications.BookSpecification;

import lombok.AllArgsConstructor;

interface JpaBookRepository extends JpaRepository<BookEntity, Long>, JpaSpecificationExecutor<BookEntity> {

}

@AllArgsConstructor
@Repository
public class MySqlBookRepository extends BookRepository {

    @Autowired
    private final JpaBookRepository jpaBookRepository;

    @Override
    public BookDto save(BookDto bookDto) {
        var bookEntity = BookEntity.builder()
                .title(bookDto.getTitle())
                .author(bookDto.getAuthor())
                .description(bookDto.getDescription())
                .visible(bookDto.getVisible())
                .category(bookDto.getCategory())
                .publicationDate(bookDto.getPublicationDate())
                .rating(bookDto.getRating())
                .unitsAvaible(bookDto.getUnitsAvaible())
                .price(bookDto.getPrice())
                .build();

        BookEntity newBook = this.jpaBookRepository.save(bookEntity);

        return new BookDto(newBook.getId(),
                newBook.getTitle(),
                newBook.getAuthor(),
                newBook.getDescription(),
                newBook.isVisible(),
                newBook.getCategory(),
                newBook.getIsbnCode(),
                newBook.getPublicationDate(),
                newBook.getRating(),
                newBook.getUnitsAvaible(),
                newBook.getPrice());
    }

    @Override
    public BookDto findById(Long id) {
        var book = this.jpaBookRepository.findById(id);

        if (book.isEmpty()) {
            throw new BookNotFoundException("Book with id " + id + " not found");
        }

        return new BookDto(
                book.get().getId(),
                book.get().getTitle(),
                book.get().getAuthor(),
                book.get().getDescription(),
                book.get().isVisible(),
                book.get().getCategory(),
                book.get().getIsbnCode(),
                book.get().getPublicationDate(),
                book.get().getRating(),
                book.get().getUnitsAvaible(),
                book.get().getPrice());
    }

    @Override
    public void deleteById(Long id) {
        this.jpaBookRepository.deleteById(id);
    }

    @Override
    public List<BookDto> findAll() {
        var books = this.jpaBookRepository.findAll();

        var booksDtos = books.stream().map(book -> new BookDto(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getDescription(),
                book.isVisible(),
                book.getCategory(),
                book.getIsbnCode(),
                book.getPublicationDate(),
                book.getRating(),
                book.getUnitsAvaible(),
                book.getPrice())).toList();

        return booksDtos;
    }

    @Override
    public List<BookDto> search(SearchBookFiltersDto filters) {
        Specification<BookEntity> spec = BookSpecification.filterBooks(filters);

        var books = this.jpaBookRepository.findAll(spec);

        var booksDtos = books.stream().map(book -> new BookDto(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getDescription(),
                book.isVisible(),
                book.getCategory(),
                book.getIsbnCode(),
                book.getPublicationDate(),
                book.getRating(),
                book.getUnitsAvaible(),
                book.getPrice())).toList();

        return booksDtos;
    }

    @Override
    public BookDto update(BookDto bookDto) {
        var existingBook =  this.jpaBookRepository.findById(bookDto.getId()).get();
        
        existingBook.setTitle(bookDto.getTitle());
        existingBook.setAuthor(bookDto.getAuthor());
        existingBook.setDescription(bookDto.getDescription());
        existingBook.setVisible(bookDto.getVisible());
        existingBook.setCategory(bookDto.getCategory());
        existingBook.setIsbnCode(bookDto.getIsbnCode());
        existingBook.setPublicationDate(bookDto.getPublicationDate());
        existingBook.setRating(bookDto.getRating());
        existingBook.setUnitsAvaible(bookDto.getUnitsAvaible());
        existingBook.setPrice(bookDto.getPrice());

        BookEntity updatedBook = this.jpaBookRepository.save(existingBook);

        return new BookDto(
                updatedBook.getId(),
                updatedBook.getTitle(),
                updatedBook.getAuthor(),
                updatedBook.getDescription(),
                updatedBook.isVisible(),
                updatedBook.getCategory(),
                updatedBook.getIsbnCode(),
                updatedBook.getPublicationDate(),
                updatedBook.getRating(),
                updatedBook.getUnitsAvaible(),
                updatedBook.getPrice());
    }

}
