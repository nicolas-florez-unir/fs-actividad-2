package com.grupo1.ms_operador.payments.domain.repositories;

import java.util.List;
import com.grupo1.ms_operador.payments.domain.dtos.PurchaseDto;

public abstract class PurchaseRepository {
    public abstract PurchaseDto save(PurchaseDto purchaseDto);
    public abstract PurchaseDto findById(Long id);
    public abstract List<PurchaseDto> findAll();
}
