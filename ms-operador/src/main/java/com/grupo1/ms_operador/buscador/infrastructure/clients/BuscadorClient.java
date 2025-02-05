package com.grupo1.ms_operador.buscador.infrastructure.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.grupo1.ms_operador.buscador.domain.dtos.BookDto;

@FeignClient(name = "gateway")
public interface BuscadorClient {
    @GetMapping("/buscador/books/{id}")
    public BookDto getBookById(@PathVariable("id") Long id);

    @GetMapping("/buscador/books?ids={ids}")
    public List<BookDto> searchBooksByIds(@PathVariable("ids") String ids);
}
