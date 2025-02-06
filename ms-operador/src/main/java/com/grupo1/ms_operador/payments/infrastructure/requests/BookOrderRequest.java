package com.grupo1.ms_operador.payments.infrastructure.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@Schema(description = "Request to order a specific book")
public class BookOrderRequest {
    @NotNull(message = "El ID del libro es obligatorio.")
    @Schema(description = "ID of the book to be ordered", example = "1", required = true)
    private Long bookId;

    @Min(value = 1, message = "La cantidad debe ser al menos 1.")
    @Schema(description = "Quantity of the book to be ordered", example = "1", required = true)
    private int quantity;
}