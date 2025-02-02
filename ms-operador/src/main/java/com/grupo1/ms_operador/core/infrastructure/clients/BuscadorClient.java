package com.grupo1.ms_operador.core.infrastructure.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "gateway")
public interface BuscadorClient {
    @GetMapping("/buscador/books/{id}")
    public String getBookById(@PathVariable("id") Long id);
}
