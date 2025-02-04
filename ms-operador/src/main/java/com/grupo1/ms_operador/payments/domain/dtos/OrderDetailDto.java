package com.grupo1.ms_operador.payments.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderDetailDto {
    private Long id;
    private Long bookId;
    private int quantity;
}
