package com.grupo1.ms_operador.payments.domain.dtos;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class OrderDto {
    private Long id;
    private Long userId;
    private List<OrderDetailDto> orderDetails;
}
