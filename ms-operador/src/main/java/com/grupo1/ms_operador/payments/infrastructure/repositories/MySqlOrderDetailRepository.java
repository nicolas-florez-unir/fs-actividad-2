package com.grupo1.ms_operador.payments.infrastructure.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupo1.ms_operador.payments.domain.dtos.OrderDetailDto;
import com.grupo1.ms_operador.payments.domain.dtos.OrderDto;
import com.grupo1.ms_operador.payments.domain.repositories.OrderDetailRepository;
import com.grupo1.ms_operador.payments.infrastructure.entities.OrderDetailEntity;
import com.grupo1.ms_operador.payments.infrastructure.mappers.OrderMapper;

import lombok.AllArgsConstructor;

interface JpaOrderDetailRepository extends JpaRepository<OrderDetailEntity, Long> {
}

@AllArgsConstructor
@Repository
public class MySqlOrderDetailRepository extends OrderDetailRepository {
    @Autowired
    private final JpaOrderDetailRepository jpaOrderDetailRepository;
    
    private final OrderMapper orderMapper;

    @Override
    public OrderDetailDto save(OrderDto orderDto, OrderDetailDto orderDetailDto) {
        var orderEntity = this.orderMapper.toOrderEntity(orderDto);

        var orderDetailEntity = new OrderDetailEntity(null, orderEntity, orderDetailDto.getBookId(), orderDetailDto.getBookQuantity(), null);

        var newDetail = this.jpaOrderDetailRepository.save(orderDetailEntity);

        return new OrderDetailDto(newDetail.getId(), newDetail.getBookId(), newDetail.getBookQuantity(), newDetail.getBookPrice());
    }
}
