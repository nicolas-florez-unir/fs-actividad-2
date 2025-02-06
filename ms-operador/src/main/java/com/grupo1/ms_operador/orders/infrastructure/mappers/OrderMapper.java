package com.grupo1.ms_operador.orders.infrastructure.mappers;

import org.mapstruct.Mapper;

import com.grupo1.ms_operador.orders.domain.dtos.OrderDto;
import com.grupo1.ms_operador.orders.infrastructure.entities.OrderEntity;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto toDto(OrderEntity order);

    OrderEntity toOrderEntity(OrderDto order);
}