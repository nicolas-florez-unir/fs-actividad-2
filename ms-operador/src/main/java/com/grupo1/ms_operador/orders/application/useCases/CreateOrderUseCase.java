package com.grupo1.ms_operador.orders.application.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.grupo1.ms_operador.orders.domain.dtos.OrderDto;
import com.grupo1.ms_operador.orders.domain.repositories.OrderRepository;

@Component
public class CreateOrderUseCase {
    @Autowired
    private OrderRepository orderRepository;

    public OrderDto execute(OrderDto orderDto) {
        return this.orderRepository.save(orderDto);
    }
}
