package com.grupo1.ms_buscador.books.infrastructure.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.grupo1.ms_buscador.books.domain.dtos.BookDto;
import com.grupo1.ms_buscador.books.domain.exceptions.BookNotFoundException;
import com.grupo1.ms_buscador.books.domain.repositories.BookRepository;
import com.grupo1.ms_buscador.books.infrastructure.entities.BookEntity;

import lombok.AllArgsConstructor;

interface JpaBookRepository extends JpaRepository<BookEntity, Long> {

    @Query("SELECT b FROM BookEntity b WHERE b.title = :title AND b.author = :author")
    List<BookEntity> findBooks(
            @Param("title") String title,
            @Param("autor") String autor,
            @Param("publicationDate") String publicationDate,
            @Param("category") String category,
            @Param("rating") Integer rating,
            @Param("visible") Boolean visible);

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

        return new BookDto(book.get().getId(), book.get().getTitle(), book.get().getAuthor(),
                book.get().getDescription(),
                book.get().isVisible(), book.get().getCategory(), book.get().getPublicationDate(),
                book.get().getRating(), book.get().getUnitsAvaible(), book.get().getPrice());
    }

    @Override
    public void deleteById(Long id) {
        this.jpaBookRepository.deleteById(id);
    }

    @Override
    public List<BookDto> findAll() {
        var books = this.jpaBookRepository.findAll();

        var booksDtos = books.stream().map(book -> new BookDto(book.getId(), book.getTitle(), book.getAuthor(),
                book.getDescription(), book.isVisible(), book.getCategory(), book.getPublicationDate(),
                book.getRating(),
                book.getUnitsAvaible(), book.getPrice())).toList();

        return booksDtos;
    }
}
