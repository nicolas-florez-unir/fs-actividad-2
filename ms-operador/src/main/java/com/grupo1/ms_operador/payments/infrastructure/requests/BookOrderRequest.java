package com.grupo1.ms_operador.payments.infrastructure.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class BookOrderRequest {
    @NotNull(message = "El ID del libro es obligatorio.")
    private Long bookId;

    @Min(value = 1, message = "La cantidad debe ser al menos 1.")
    private int quantity;
}