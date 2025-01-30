package com.grupo1.ms_buscador.books.infrastructure.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo1.ms_buscador.books.application.CreateBookUseCase;
import com.grupo1.ms_buscador.books.application.DeleteBookByIdUseCase;
import com.grupo1.ms_buscador.books.application.GetAllBooksUseCase;
import com.grupo1.ms_buscador.books.application.GetBookByIdUseCase;
import com.grupo1.ms_buscador.books.domain.dtos.BookDto;
import com.grupo1.ms_buscador.books.domain.exceptions.BookNotFoundException;
import com.grupo1.ms_buscador.books.infrastructure.requests.CreateBookRequest;
import com.grupo1.ms_buscador.books.infrastructure.requests.SearchBookRequest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {

    private final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    GetAllBooksUseCase getAllBooksUseCase;

    @Autowired
    CreateBookUseCase createBookUseCase;

    @Autowired
    GetBookByIdUseCase getBookByIdUseCase;

    @Autowired
    DeleteBookByIdUseCase deleteBookByIdUseCase;

    @GetMapping("/all")
    public List<BookDto> getAllBooks() {
        return this.getAllBooksUseCase.execute();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getById(@PathVariable("id") @Min(0) Integer bookId) {
        try {
            var book = this.getBookByIdUseCase.execute(bookId.longValue());
            return ResponseEntity.ok(book);
        } catch (Exception e) {
            if (e instanceof BookNotFoundException) {
                return ResponseEntity.notFound().build();
            }

            this.logger.error(e.getMessage(), e);

            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/search")
    public List<BookDto> searchBook(@ModelAttribute SearchBookRequest request) {
        System.out.println(request.toString());
        return this.getAllBooksUseCase.execute();
    }

    @PostMapping("/create")
    public BookDto createBook(@Valid @RequestBody CreateBookRequest request) {
        var dto = new BookDto(null, request.getTitle(), request.getAuthor(),
                request.getDescription(), request.getVisible(), request.getCategory(), request.getPublicationDate(),
                request.getRating(), request.getUnitsAvaible(), request.getPrice());

        return this.createBookUseCase.execute(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") @Min(0) Integer bookId) {
        try {
            this.deleteBookByIdUseCase.execute(bookId.longValue());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

}
