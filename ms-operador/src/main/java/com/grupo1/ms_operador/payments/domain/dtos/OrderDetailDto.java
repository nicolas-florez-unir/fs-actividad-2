package com.grupo1.ms_operador.payments.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDto {
    private Long id;
    private Long bookId;
    private int bookQuantity;
    private double bookPrice;
}
