package com.grupo1.ms_operador.payments.application;

import org.springframework.stereotype.Component;
import com.grupo1.ms_operador.payments.domain.dtos.BookDto;
import lombok.AllArgsConstructor;

//Validar caso de uso de libro
@AllArgsConstructor
@Component
public class ValidateBookUseCase {
    public BookDto validateBook(Long bookId) {
        // if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
        //     throw new BookNotAvailableException("El libro no existe.");
        // }

        // BookDto book = response.getBody();
        // if (!book.getVisible() || book.getUnitsAvaible() <= 0) {
        //     throw new BookNotAvailableException("El libro no está disponible.");
        // }

        return new BookDto();
    }
}
