package com.grupo1.ms_operador.payments.infrastructure.requests;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import jakarta.validation.constraints.NotNull;

@Getter
@Schema(description = "Request to create a new order")
public class CreateOrderRequest {
    @NotNull(message = "El ID del usuario es obligatorio.")
    @Schema(description = "ID of the user placing the order", example = "1", required = true)
    private Long userId;

    @NotNull(message = "El ID del libro es obligatorio.")
    @Schema(description = "List of book orders", required = true)
    private List<BookOrderRequest> bookOrderRequest;
}