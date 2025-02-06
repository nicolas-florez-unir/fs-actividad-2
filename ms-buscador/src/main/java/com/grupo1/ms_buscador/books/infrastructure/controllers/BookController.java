package com.grupo1.ms_buscador.books.infrastructure.controllers;

import java.util.List;

import com.grupo1.ms_buscador.books.infrastructure.requests.UpdateBookRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import com.grupo1.ms_buscador.books.application.CreateBookUseCase;
import com.grupo1.ms_buscador.books.application.DeleteBookByIdUseCase;
import com.grupo1.ms_buscador.books.application.GetAllBooksUseCase;
import com.grupo1.ms_buscador.books.application.GetBookByIdUseCase;
import com.grupo1.ms_buscador.books.application.SearchBookUseCase;
import com.grupo1.ms_buscador.books.application.UpdateBookUseCase;
import com.grupo1.ms_buscador.books.domain.dtos.BookDto;
import com.grupo1.ms_buscador.books.domain.dtos.SearchBookFiltersDto;
import com.grupo1.ms_buscador.books.domain.exceptions.BookNotFoundException;
import com.grupo1.ms_buscador.books.infrastructure.requests.CreateBookRequest;
import com.grupo1.ms_buscador.books.infrastructure.requests.SearchBookRequest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/books")
@Tag(name = "Book Management System", description = "Operations pertaining to book in Book Management System")
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

    @Autowired
    SearchBookUseCase searchBookUseCase;

    @Autowired
    UpdateBookUseCase updateBookUseCase;

    @Operation(summary = "Search books", description = "Search books based on filters", responses = {
            @ApiResponse(description = "List of books", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDto.class)))
    })
    @GetMapping()
    public ResponseEntity<List<BookDto>> searchBook(@Parameter(description = "Search filters") @ModelAttribute SearchBookRequest request) {
        List<Long> idsAsList = null;

        try {
            var idsSplitted = request.getIds() != null ? request.getIds().split(",") : new String[0];
            if (idsSplitted.length > 0) {
                idsAsList = List.of(idsSplitted)
                        .stream()
                        .map(Long::parseLong)
                        .toList();
            }
        } catch (NumberFormatException e) {
            this.logger.error("No se puede convertir a Long la lista de ids" + e.getMessage());
        }

        var filtersDto = SearchBookFiltersDto
                .builder()
                .ids(idsAsList)
                .title(request.getTitle())
                .author(request.getAuthor())
                .description(request.getDescription())
                .publicationDate(request.getPublicationDate())
                .category(request.getCategory())
                .visible(request.getVisible())
                .isbnCode(request.getIsbnCode())
                .build();

        return ResponseEntity.ok(this.searchBookUseCase.execute(filtersDto));
    }

    @Operation(summary = "Get a book by Id", description = "Get a book by its Id", responses = {
            @ApiResponse(description = "Book details", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDto.class))),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getById(@Parameter(description = "Book id from which book object will retrieve", required = true) @PathVariable("id") @Min(0) Integer bookId) {
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
    
    @Operation(summary = "Create a book", description = "Create a new book", responses = {
            @ApiResponse(description = "Book Created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDto.class)))
    })
    @PostMapping()
    public BookDto createBook(@Parameter(description = "Book object store in database table", required = true) @Valid @RequestBody CreateBookRequest request) {
        var dto = new BookDto(
                null,
                request.getTitle(),
                request.getAuthor(),
                request.getDescription(),
                request.getVisible(),
                request.getCategory(),
                request.getIsbnCode(),
                request.getPublicationDate(),
                request.getRating(),
                request.getUnitsAvaible(),
                request.getPrice());

        return this.createBookUseCase.execute(dto);
    }

    @Operation(summary = "Delete a book", description = "Delete a book by its Id", responses = {
            @ApiResponse(responseCode = "200", description = "Book deleted"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@Parameter(description = "Book Id from which book object will delete from database table", required = true) @PathVariable("id") @Min(0) Integer bookId) {
        try {
            this.deleteBookByIdUseCase.execute(bookId.longValue());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @Operation(summary = "Update a book", description = "Update a book by its Id", responses = {
            @ApiResponse(description = "Book updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(@Parameter(description = "Book Id to update book object", required = true) @PathVariable("id") @Min(0) Long bookId, @Parameter(description = "Update book object", required = true) @Valid @RequestBody UpdateBookRequest request) {

        var existingBook = this.getBookByIdUseCase.execute(bookId);
        if (existingBook == null) {
            throw new BookNotFoundException("Book with id " + bookId + " not found");
        }

        var bookDto = new BookDto(
                bookId,
                request.getTitle(),
                request.getAuthor(),
                request.getDescription(),
                request.getVisible(),
                request.getCategory(),
                request.getIsbnCode(),
                request.getPublicationDate(),
                request.getRating(),
                request.getUnitsAvaible(),
                request.getPrice());

        try {
            return ResponseEntity.ok(this.updateBookUseCase.execute(bookId, bookDto));
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Partially update a book", description = "Partially update a book by its Id", responses = {
            @ApiResponse(description = "Book partially updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<BookDto> partiallyUpdateBook(@Parameter(description = "Book Id to partially update book object", required = true) @PathVariable("id") Long bookId, @Parameter(description = "Partially update book object", required = true) @RequestBody UpdateBookRequest request) {

        var existingBook = this.getBookByIdUseCase.execute(bookId);
        if (existingBook == null) {
            throw new BookNotFoundException("Book with id " + bookId + " not found");
        }

        var updatedBookDto = new BookDto(
                bookId,
                request.getTitle() != null ? request.getTitle() : existingBook.getTitle(),
                request.getAuthor() != null ? request.getAuthor() : existingBook.getAuthor(),
                request.getDescription() != null ? request.getDescription() : existingBook.getDescription(),
                request.getVisible() != null ? request.getVisible() : existingBook.getVisible(),
                request.getCategory() != null ? request.getCategory() : existingBook.getCategory(),
                request.getIsbnCode() != null ? request.getIsbnCode() : existingBook.getIsbnCode(),
                request.getPublicationDate() != null ? request.getPublicationDate() : existingBook.getPublicationDate(),
                request.getRating() != 0 ? request.getRating() : existingBook.getRating(),
                request.getUnitsAvaible() != 0 ? request.getUnitsAvaible() : existingBook.getUnitsAvaible(),
                request.getPrice() != 0 ? request.getPrice() : existingBook.getPrice());

        try {
            return ResponseEntity.ok(updateBookUseCase.execute(bookId, updatedBookDto));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Test endpoint", description = "Test endpoint to check if the service is running")
    @GetMapping("/test")
    public String test() {
        return "Hello from books";
    }
}