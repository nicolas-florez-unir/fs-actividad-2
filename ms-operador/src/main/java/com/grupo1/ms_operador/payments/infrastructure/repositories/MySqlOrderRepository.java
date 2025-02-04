package com.grupo1.ms_operador.payments.infrastructure.repositories;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupo1.ms_operador.payments.application.mappers.OrderMapper;
import com.grupo1.ms_operador.payments.domain.dtos.OrderDetailDto;
import com.grupo1.ms_operador.payments.domain.dtos.OrderDto;
import com.grupo1.ms_operador.payments.domain.exceptions.PurchaseNotFoundException;
import com.grupo1.ms_operador.payments.domain.repositories.OrderDetailRepository;
import com.grupo1.ms_operador.payments.domain.repositories.OrderRepository;
import com.grupo1.ms_operador.payments.infrastructure.entities.OrderEntity;
import lombok.AllArgsConstructor;

interface JpaOrderRepository extends JpaRepository<OrderEntity, Long> {
}

@AllArgsConstructor
@Repository
public class MySqlOrderRepository extends OrderRepository {
        @Autowired
        private final JpaOrderRepository jpaOrderRepository;

        @Autowired
        private final OrderDetailRepository orderDetailRepository;

        private final OrderMapper orderMapper;

        @Override
        public OrderDto save(OrderDto orderDto) {
                var newOrder = this.jpaOrderRepository.save(this.orderMapper.toOrderEntity(orderDto));

                var orderDetails = orderDto.getOrderDetails().stream()
                                .map(detail -> orderDetailRepository.save(orderDto, detail))
                                .toList();

                return new OrderDto(newOrder.getId(), newOrder.getUserId(), orderDetails);
        }

        @Override
        public List<OrderDto> findAll() {
                return this.jpaOrderRepository.findAll().stream()
                                .map(order -> {
                                        List<OrderDetailDto> detailsDto = order.getOrderDetails().stream()
                                                        .map(detail -> new OrderDetailDto(order.getId(),
                                                                        detail.getBookId(),
                                                                        detail.getQuantity()))
                                                        .toList();

                                        return new OrderDto(order.getId(), order.getUserId(), detailsDto);
                                })
                                .toList();
        }

        @Override
        public OrderDto findById(Long id) {
                var order = this.jpaOrderRepository.findById(id)
                                .orElseThrow(() -> new PurchaseNotFoundException(
                                                "No se encontró la compra con id " + id));

                List<OrderDetailDto> detailsDto = order.getOrderDetails().stream()
                                .map(detail -> new OrderDetailDto(order.getId(), detail.getBookId(),
                                                detail.getQuantity()))
                                .toList();

                return new OrderDto(order.getId(), order.getUserId(), detailsDto);
        }
}
