package com.grupo1.ms_operador.payments.application.mappers;

import org.mapstruct.Mapper;

import com.grupo1.ms_operador.payments.domain.dtos.OrderDetailDto;
import com.grupo1.ms_operador.payments.infrastructure.entities.OrderDetailEntity;

@Mapper
public interface OrderDetailMapper {
    OrderDetailDto toOrderDetailDto(OrderDetailEntity orderDetailEntity);
    
    OrderDetailEntity toOrderDetailEntity(OrderDetailDto orderDetailDto);
}
