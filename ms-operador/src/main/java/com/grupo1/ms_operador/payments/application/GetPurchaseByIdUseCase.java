package com.grupo1.ms_operador.payments.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.grupo1.ms_operador.payments.domain.dtos.PurchaseDto;
import com.grupo1.ms_operador.payments.domain.repositories.PurchaseRepository;
import lombok.AllArgsConstructor;

//Obtener compra por caso de uso de ld
@AllArgsConstructor
@Component
public class GetPurchaseByIdUseCase {

    @Autowired
    private final PurchaseRepository purchaseRepository;

    public PurchaseDto execute(Long id) {
        return purchaseRepository.findById(id);
    }
}
