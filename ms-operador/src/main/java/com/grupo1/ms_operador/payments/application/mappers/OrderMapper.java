package com.grupo1.ms_operador.payments.application.mappers;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.grupo1.ms_operador.payments.domain.dtos.OrderDto;
import com.grupo1.ms_operador.payments.infrastructure.entities.OrderEntity;

@Mapper
public interface OrderMapper {
    @Mapping(target = "orderDetails", source = "orderEntity.orderDetails")
    OrderDto toOrderDto(OrderEntity orderEntity);
    
    OrderEntity toOrderEntity(OrderDto orderDto);
}
