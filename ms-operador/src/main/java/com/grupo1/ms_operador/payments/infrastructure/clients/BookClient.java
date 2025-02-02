package com.grupo1.ms_operador.payments.infrastructure.clients;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import com.grupo1.ms_operador.payments.domain.dtos.BookDto;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class BookClient {

    private final RestTemplate restTemplate;

    private final String BOOKS_API_URL = "http://buscador/books";

    public BookDto getBookById(Long bookId) {
        String url = BOOKS_API_URL + "/" + bookId;
        ResponseEntity<BookDto> response = restTemplate.getForEntity(url, BookDto.class);
        return response.getBody();
    }
}
