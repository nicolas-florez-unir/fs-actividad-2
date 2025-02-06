package com.grupo1.ms_operador.orders.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDetailDto {
    private Long id;
    private Long bookId;
    private int bookQuantity;
    private double bookPrice;
}
