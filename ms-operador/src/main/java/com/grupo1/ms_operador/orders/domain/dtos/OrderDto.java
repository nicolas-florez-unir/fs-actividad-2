package com.grupo1.ms_operador.orders.domain.dtos;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDto {
    private Long id;
    private Long userId;
    private Double totalPrice;
    private List<OrderDetailDto> orderDetails;
}
