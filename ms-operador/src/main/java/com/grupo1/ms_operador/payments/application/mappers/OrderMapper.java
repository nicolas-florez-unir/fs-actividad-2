package com.grupo1.ms_operador.payments.application.mappers;

import org.mapstruct.Mapper;

import com.grupo1.ms_operador.payments.domain.dtos.OrderDto;
import com.grupo1.ms_operador.payments.infrastructure.entities.OrderEntity;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto toDto(OrderEntity order);

    OrderEntity toOrderEntity(OrderDto order);
}