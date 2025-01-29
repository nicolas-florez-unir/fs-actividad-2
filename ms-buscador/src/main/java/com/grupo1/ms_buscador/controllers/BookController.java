package com.grupo1.ms_buscador.controllers;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.swagger.v3.oas.annotations.Parameter;
import com.grupo1.ms_buscador.controllers.model.*;
import com.grupo1.ms_buscador.data.utils.BookRepository;
import com.grupo1.ms_buscador.data.entity.BookEntity;
import com.grupo1.ms_buscador.service.BooksService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody;


import java.util.Collections;
import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@Slf4j

@Tag(name = "Book Controller", description = "Microservicio encargado del manejo de operaciones CRUD sobre libros en una base de datos en memoria.")

public class BookController {

    private final BookRepository bookRepository;
    private final BooksService service;
    @GetMapping("/books")
    @Operation(
            operationId = "Obtener libros",
            description = "Operacion de lectura",
            summary = "Se devuelve una lista de todos los libros almacenados en la base de datos.")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookEntity.class)))
    @ApiResponse(
            responseCode = "404",
            description = "No se han encontrado libros.")
    public ResponseEntity<List<BookEntity>> getBooks(
            @RequestHeader Map<String, String> headers,
            @Parameter(name = "title", description  = "Titulo del libro. No tiene por que ser exacto", example = "El Quijote", required = false)
            @RequestParam(required = false) String title,
            @Parameter(name = "author", description = "Autor del libro. Debe ser exacto", example = "Cervantes", required = false)
            @RequestParam(required = false) String author,
            @Parameter(name = "description", description = "Descripcion del libro. No tiene por que ser exacta", example = "Estupendo", required = false)
            @RequestParam(required = false) String description,
            @Parameter(name = "publicationDate", description = "Fecha de publicación del libro. No tiene por que ser exacta", example = "16/05", required = false)
            @RequestParam(required = false) String publicationDate,
            @Parameter(name = "category", description = "Categoria del libro. No tiene por que ser exacta", example = "Novela", required = false)
            @RequestParam(required = false) String category,
            @Parameter(name = "rating", description = "Rating del libro. Debe ser exacto", example = "5", required = false)
            @RequestParam(required = false) Integer rating,
            @Parameter(name = "visibility", description = "Visibilidad del libro. true o false", example = "true", required = false)
            @RequestParam(required = false) Boolean visibility) {

        List<BookEntity> book = service.getBooks(title, author, description, visibility, category, publicationDate, rating );

        if (!book.isEmpty()) {
            return ResponseEntity.ok(book);
        } else {
            return ResponseEntity.ok(Collections.emptyList());
        }
    }


    @GetMapping("/books/{id}")
    @Operation(
            operationId = "Obtener libro por BookId",
            description = "Operación de lectura",
            summary = "Se devuelve un libro que se encuentre almacenado en la base de datos por su identificador.")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookEntity.class)))
    @ApiResponse(
            responseCode = "404",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "No se ha encontrado el libro con el identificador proporcionado.")

    public ResponseEntity<BookEntity> getBook(@PathVariable String BookId) {

        log.info("Request received for book {}", BookId);
        BookEntity book = service.getBook(BookId);

        if (book != null) {
            return ResponseEntity.ok(book);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/books/{id}")
    @Operation(
            operationId = "Eliminar libro por BookId",
            description = "Operación de escritura",
            summary = "Se elimina un libro que se encuentre almacenado en la base de datos por su identificador.")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookEntity.class)))
    @ApiResponse(
            responseCode = "404",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "No se ha encontrado el libro con el identificador proporcionado.")
    public ResponseEntity<BookEntity> deleteBook(@PathVariable String BookId) {

        Boolean removed = service.removeBook(BookId);

        if (Boolean.TRUE.equals(removed)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/books")
    @Operation(
            operationId = "Insertar libro",
            description = "Operación de escritura",
            summary = "Se inserta un libro en la base de datos.",
            requestBody = @RequestBody(
                    description = "Libro a insertar",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookEntity.class))))
    @ApiResponse(
            responseCode = "201",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookEntity.class)))
    @ApiResponse(
            responseCode = "400",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "No se ha podido insertar el libro.")
    @ApiResponse(
            responseCode = "409",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "Ya existe un libro con el mismo identificador.")
    @ApiResponse(
            responseCode = "500",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "Error interno en el servidor.")
    @ApiResponse(
            responseCode = "404",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "No se ha encontrado el libro con el identificador proporcionado.")
    public ResponseEntity<BookEntity> addBook(@RequestBody CreateBookRequest request) {

        BookEntity createdBook = service.createBook(request);

        if (createdBook != null) {
            return ResponseEntity.ok(createdBook);
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

    @PatchMapping("/books/{id}")
    @Operation(
            operationId = "Actualizar libro",
            description = "Operación de escritura",
            summary = "Se actualiza parcialmente un libro en la base de datos.",
            requestBody = @RequestBody(
                    description = "Libro a actualizar",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookEntity.class))))
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookEntity.class)))
    @ApiResponse(
            responseCode = "400",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "No se ha podido actualizar el libro.")
    @ApiResponse(
            responseCode = "404",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "No se ha encontrado el libro con el identificador proporcionado.")
    public ResponseEntity<BookEntity> updateBook(@PathVariable String BookId, @RequestBody String request) {

        BookEntity updatedBook = service.updateBook(BookId, request);

        if (updatedBook != null) {
            return ResponseEntity.ok(updatedBook);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/books/{id}")
    @Operation(
            operationId = "Actualizar totalmente un libro",
            description = "Operación de escritura",
            summary = "Se actualiza totalmente un libro en la base de datos.",
            requestBody = @RequestBody(
                    description = "Libro a actualizar",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookEntity.class))))
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookEntity.class)))
    @ApiResponse(
            responseCode = "400",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "No se ha podido actualizar el libro.")
    @ApiResponse(
            responseCode = "404",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "No se ha encontrado el libro con el identificador proporcionado.")
    public ResponseEntity<BookEntity> updateBook(@PathVariable String BookId, @RequestBody BookDto request) {

        BookEntity updatedBook = service.updateBook(BookId, request);

        if (updatedBook != null) {
            return ResponseEntity.ok(updatedBook);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping("/search")
    @Operation(
            operationId = "Buscar libros",
            description = "Operacion de lectura",
            summary = "Se devuelve una lista de todos los libros almacenados en la base de datos " +
                    "que cumplan con diversos criterios de busqueda.")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookEntity.class)))
    @ApiResponse(
            responseCode = "404",
            description = "No se han encontrado libros.")
    public ResponseEntity<List<BookEntity>> searchBooks(
            @RequestHeader Map<String, String> headers,
            @Parameter(name = "title", description  = "Titulo del libro. No tiene por que ser exacto", example = "El Quijote", required = false)
            @RequestParam(required = false) String title,
            @Parameter(name = "author", description = "Autor del libro. Debe ser exacto", example = "Cervantes", required = false)
            @RequestParam(required = false) String author,
            @Parameter(name = "description", description = "Descripcion del libro. No tiene por que ser exacta", example = "Estupendo", required = false)
            @RequestParam(required = false) String description,
            @Parameter(name = "publicationDate", description = "Fecha de publicación del libro. No tiene por que ser exacta", example = "16/05", required = false)
            @RequestParam(required = false) String publicationDate,
            @Parameter(name = "category", description = "Categoria del libro. No tiene por que ser exacta", example = "Novela", required = false)
            @RequestParam(required = false) String category,
            @Parameter(name = "rating", description = "Rating del libro. Debe ser exacto", example = "5", required = false)
            @RequestParam(required = false) Integer rating,
            @Parameter(name = "visibility", description = "Visibilidad del libro. true o false", example = "true", required = false)
            @RequestParam(required = false) Boolean visibility,
            @Parameter(name = "price", description = "Precio del libro. Debe ser exacto", example = "10.0", required = false)
            @RequestParam(required = false) Double price,
            @Parameter(name = "unitsAvaible", description = "Unidades disponibles del libro. Debe ser exacto", example = "10", required = false)
            @RequestParam(required = false) Integer unitsAvaible) {

        List<BookEntity> book = bookRepository.search(title, author, description, visibility,
                category, publicationDate, rating, price, unitsAvaible);


        if (!book.isEmpty()) {
            return ResponseEntity.ok(book);
        } else {
            return ResponseEntity.ok(Collections.emptyList());
        }

    }



}
