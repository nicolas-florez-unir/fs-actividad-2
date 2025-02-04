package com.grupo1.ms_operador.payments.infrastructure.controllers;

import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.grupo1.ms_operador.payments.domain.dtos.OrderDetailDto;
import com.grupo1.ms_operador.payments.domain.dtos.OrderDto;
import com.grupo1.ms_operador.payments.domain.exceptions.BookNotAvailableException;
import com.grupo1.ms_operador.payments.infrastructure.requests.CreateOrderRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo1.ms_operador.buscador.infrastructure.clients.BuscadorClient;
import com.grupo1.ms_operador.payments.application.useCases.CreateOrderUseCase;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private BuscadorClient buscadorClient;

    @Autowired
    private CreateOrderUseCase createOrderUseCase;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        try {
            var booksOrdersRequest = createOrderRequest.getBookOrderRequest();

            var bookOrdersDto = booksOrdersRequest.stream().map(bookOrder -> {
                var book = buscadorClient.getBookById(bookOrder.getBookId());

                if (!book.getVisible())
                    throw new BookNotAvailableException("No está visible el libro " + book.getTitle());

                if (book.getUnitsAvaible() < bookOrder.getQuantity())
                    throw new BookNotAvailableException("No hay stock suficiente para el libro " + book.getTitle());

                return new OrderDetailDto(null, book.getId(), bookOrder.getQuantity());
            });

            var orderDto = new OrderDto(null, createOrderRequest.getUserId(), bookOrdersDto.toList());

            this.createOrderUseCase.execute(orderDto);
            return ResponseEntity.ok(null);
        } catch (BookNotAvailableException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            this.logger.error("Error al procesar la compra", e);
            return ResponseEntity.internalServerError().body("Error al procesar la compra.");
        }
    }

}
