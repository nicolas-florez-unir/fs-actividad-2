package com.grupo1.ms_operador.payments.domain.repositories;

import com.grupo1.ms_operador.payments.domain.dtos.OrderDetailDto;
import com.grupo1.ms_operador.payments.domain.dtos.OrderDto;

public abstract class OrderDetailRepository {
    public abstract OrderDetailDto save(OrderDto orderDto, OrderDetailDto orderDetailDto);
}
