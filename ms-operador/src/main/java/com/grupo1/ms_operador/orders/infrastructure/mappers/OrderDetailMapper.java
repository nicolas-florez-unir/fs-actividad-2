package com.grupo1.ms_operador.orders.infrastructure.mappers;

import org.mapstruct.Mapper;

import com.grupo1.ms_operador.orders.domain.dtos.OrderDetailDto;
import com.grupo1.ms_operador.orders.infrastructure.entities.OrderDetailEntity;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {
    OrderDetailDto toOrderDetailDto(OrderDetailEntity orderDetails);

    OrderDetailEntity toOrderDetailEntity(OrderDetailDto orderDetails);
}
