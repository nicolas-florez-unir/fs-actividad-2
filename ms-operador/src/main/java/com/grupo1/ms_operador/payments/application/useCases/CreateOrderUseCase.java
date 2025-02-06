package com.grupo1.ms_operador.payments.application.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.grupo1.ms_operador.payments.domain.dtos.OrderDto;
import com.grupo1.ms_operador.payments.domain.repositories.OrderRepository;

@Component
public class CreateOrderUseCase {
    @Autowired
    private OrderRepository orderRepository;

    public OrderDto execute(OrderDto orderDto) {
        return this.orderRepository.save(orderDto);
    }
}
