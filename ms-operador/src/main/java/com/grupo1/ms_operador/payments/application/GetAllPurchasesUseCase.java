package com.grupo1.ms_operador.payments.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.grupo1.ms_operador.payments.domain.dtos.PurchaseDto;
import com.grupo1.ms_operador.payments.domain.repositories.PurchaseRepository;
import java.util.List;
import lombok.AllArgsConstructor;

//Caso de uso Obtener todas las compras
@AllArgsConstructor
@Component
public class GetAllPurchasesUseCase {

    @Autowired
    private final PurchaseRepository purchaseRepository;

    public List<PurchaseDto> execute() {
        return purchaseRepository.findAll();
    }
}
