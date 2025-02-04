package com.grupo1.ms_operador.payments.domain.repositories;

import java.util.List;

import com.grupo1.ms_operador.payments.domain.dtos.OrderDto;

public abstract class OrderRepository {
    public abstract OrderDto save(OrderDto orderDto);
    public abstract OrderDto findById(Long id);
    public abstract List<OrderDto> findAll();
}
