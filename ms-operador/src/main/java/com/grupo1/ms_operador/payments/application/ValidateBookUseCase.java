package com.grupo1.ms_operador.payments.application;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.grupo1.ms_operador.payments.domain.dtos.BookDto;
import com.grupo1.ms_operador.payments.domain.exceptions.BookNotAvailableException;
import lombok.AllArgsConstructor;

//Validar caso de uso de libro
@AllArgsConstructor
@Component
public class ValidateBookUseCase {
    private final RestTemplate restTemplate;
    private final String BOOKS_API_URL = "http://ms-buscador/books/";

    public BookDto validateBook(Long bookId) {
        ResponseEntity<BookDto> response = restTemplate.getForEntity(BOOKS_API_URL + bookId, BookDto.class);

        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new BookNotAvailableException("El libro no existe.");
        }

        BookDto book = response.getBody();
        if (!book.getVisible() || book.getUnitsAvaible() <= 0) {
            throw new BookNotAvailableException("El libro no está disponible.");
        }

        return book;
    }
}
