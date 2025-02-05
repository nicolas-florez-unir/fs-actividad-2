package com.grupo1.ms_operador.payments.infrastructure.requests;

import java.util.List;
import lombok.Getter;
import jakarta.validation.constraints.NotNull;

@Getter
public class CreateOrderRequest {
    @NotNull(message = "El ID del usuario es obligatorio.")
    private Long userId;

    @NotNull(message = "El ID del libro es obligatorio.")
    private List<BookOrderRequest> bookOrderRequest;
}