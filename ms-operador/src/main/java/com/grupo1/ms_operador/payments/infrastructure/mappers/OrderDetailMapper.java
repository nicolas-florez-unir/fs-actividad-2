package com.grupo1.ms_operador.payments.infrastructure.mappers;

import org.mapstruct.Mapper;

import com.grupo1.ms_operador.payments.domain.dtos.OrderDetailDto;
import com.grupo1.ms_operador.payments.infrastructure.entities.OrderDetailEntity;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {
    OrderDetailDto toOrderDetailDto(OrderDetailEntity orderDetails);

    OrderDetailEntity toOrderDetailEntity(OrderDetailDto orderDetails);
}
