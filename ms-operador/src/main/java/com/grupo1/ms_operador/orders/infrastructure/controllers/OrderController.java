package com.grupo1.ms_operador.orders.infrastructure.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo1.ms_operador.buscador.domain.dtos.BookDto;
import com.grupo1.ms_operador.buscador.infrastructure.clients.BuscadorClient;
import com.grupo1.ms_operador.orders.application.useCases.CreateOrderUseCase;
import com.grupo1.ms_operador.orders.application.useCases.GetOrderByIdUseCase;
import com.grupo1.ms_operador.orders.domain.dtos.OrderDetailDto;
import com.grupo1.ms_operador.orders.domain.dtos.OrderDto;
import com.grupo1.ms_operador.orders.domain.exceptions.BookNotAvailableException;
import com.grupo1.ms_operador.orders.infrastructure.requests.CreateOrderRequest;

import lombok.AllArgsConstructor;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/orders")
@Tag(name = "Order Controller", description = "Controller for order management and administration")
public class OrderController {
    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private BuscadorClient buscadorClient;

    @Autowired
    private CreateOrderUseCase createOrderUseCase;
    @Autowired
    private GetOrderByIdUseCase getOrderByIdUseCase;

    @Operation(summary = "Create a new order", description = "Create a new order based on the request provided.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input or book not available",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody @Valid CreateOrderRequest createOrderRequest) {
        try {
            var booksOrdersRequest = createOrderRequest.getBookOrderRequest();

            var booksIds = createOrderRequest.getBookOrderRequest().stream()
                    .map(bookOrder -> bookOrder.getBookId().toString())
                    .toList();

            var booksByIds = this.buscadorClient.searchBooksByIds(String.join(",", booksIds).toString());

            var bookOrdersDto = booksOrdersRequest.stream().map(bookOrder -> {
                BookDto book = null;

                book = booksByIds.stream().filter(b -> b.getId() == bookOrder.getBookId()).findFirst().orElse(null);

                if (book == null)
                    throw new BookNotAvailableException("No se encontró el libro con id " + bookOrder.getBookId());

                if (!book.getVisible())
                    throw new BookNotAvailableException("No está visible el libro " + book.getTitle());

                if (book.getUnitsAvaible() < bookOrder.getQuantity())
                    throw new BookNotAvailableException("No hay stock suficiente para el libro " + book.getTitle());

                return new OrderDetailDto(null, book.getId(), bookOrder.getQuantity(), book.getPrice());
            }).toList();

            var totalPrice = bookOrdersDto.stream()
                    .map(orderDetail -> orderDetail.getBookPrice() * orderDetail.getBookQuantity())
                    .reduce(0.0, Double::sum);

            var orderDto = new OrderDto(null, createOrderRequest.getUserId(), totalPrice, bookOrdersDto);

            return ResponseEntity.created(null).body(this.createOrderUseCase.execute(orderDto));
        } catch (BookNotAvailableException e) {
            return ResponseEntity.badRequest().body(e.getMessage().toString());
        } catch (Exception e) {
            this.logger.error("Error al procesar la compra", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get order by ID", description = "Get an order by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order obtain successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderDto.class))),
            @ApiResponse(responseCode = "404", description = "Order not found",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable("id") Long id) {
        var order = this.getOrderByIdUseCase.execute(id);

        if(order.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(order.get());
    }
}
