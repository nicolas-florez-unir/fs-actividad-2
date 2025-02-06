package com.grupo1.ms_operador.orders.application.useCases;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.grupo1.ms_operador.orders.domain.dtos.OrderDto;
import com.grupo1.ms_operador.orders.domain.repositories.OrderRepository;

@Component
public class GetOrderByIdUseCase {
    @Autowired
    private OrderRepository orderRepository;

    public Optional<OrderDto> execute(Long id) {
        return this.orderRepository.findById(id);
    }
}
