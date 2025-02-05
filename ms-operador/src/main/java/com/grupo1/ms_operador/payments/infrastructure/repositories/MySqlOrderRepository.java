package com.grupo1.ms_operador.payments.infrastructure.repositories;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupo1.ms_operador.payments.application.mappers.OrderMapper;
import com.grupo1.ms_operador.payments.domain.dtos.OrderDto;
import com.grupo1.ms_operador.payments.domain.exceptions.PurchaseNotFoundException;
import com.grupo1.ms_operador.payments.domain.repositories.OrderRepository;
import com.grupo1.ms_operador.payments.infrastructure.entities.OrderDetailEntity;
import com.grupo1.ms_operador.payments.infrastructure.entities.OrderEntity;
import lombok.AllArgsConstructor;

interface JpaOrderRepository extends JpaRepository<OrderEntity, Long> {
}

@AllArgsConstructor
@Repository
public class MySqlOrderRepository extends OrderRepository {
        @Autowired
        private final JpaOrderRepository jpaOrderRepository;

        private final OrderMapper orderMapper;

        @Override
        public OrderDto save(OrderDto orderDto) {
                var orderEntity = orderMapper.toOrderEntity(orderDto);

                // Asignar la orden a cada detalle
                for (OrderDetailEntity detail : orderEntity.getOrderDetails()) {
                        detail.setOrder(orderEntity); // Asegurar que cada detalle tiene la orden asignada
                }

                var totalPrice = orderDto.getOrderDetails().stream()
                                .map(orderDetail -> orderDetail.getBookPrice() * orderDetail.getBookQuantity())
                                .reduce(0.0, Double::sum);

                orderEntity.setCreatedAt(LocalDateTime.now().toString());
                orderEntity.setTotalPrice(totalPrice);

                var newOrder = this.jpaOrderRepository.save(orderEntity);

                return this.orderMapper.toDto(newOrder);
        }

        @Override
        public List<OrderDto> findAll() {
                return this.jpaOrderRepository.findAll().stream()
                                .map(order -> orderMapper.toDto(order))
                                .toList();
        }

        @Override
        public OrderDto findById(Long id) {
                var order = this.jpaOrderRepository.findById(id)
                                .orElseThrow(() -> new PurchaseNotFoundException(
                                                "No se encontró la compra con id " + id));

                return this.orderMapper.toDto(order);
        }
}
