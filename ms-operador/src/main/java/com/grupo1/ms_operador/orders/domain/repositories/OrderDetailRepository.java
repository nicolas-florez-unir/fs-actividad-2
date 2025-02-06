package com.grupo1.ms_operador.orders.domain.repositories;

import com.grupo1.ms_operador.orders.domain.dtos.OrderDetailDto;
import com.grupo1.ms_operador.orders.domain.dtos.OrderDto;

public abstract class OrderDetailRepository {
    public abstract OrderDetailDto save(OrderDto orderDto, OrderDetailDto orderDetailDto);
}
