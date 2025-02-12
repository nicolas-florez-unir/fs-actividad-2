package com.grupo1.ms_operador.orders.domain.repositories;

import java.util.List;
import java.util.Optional;

import com.grupo1.ms_operador.orders.domain.dtos.OrderDto;

public abstract class OrderRepository {
    public abstract OrderDto save(OrderDto orderDto);
    public abstract Optional<OrderDto> findById(Long id);
    public abstract List<OrderDto> findAll();
}
