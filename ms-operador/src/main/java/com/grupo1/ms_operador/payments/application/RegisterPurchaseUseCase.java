package com.grupo1.ms_operador.payments.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.grupo1.ms_operador.payments.domain.dtos.PurchaseDto;
import com.grupo1.ms_operador.payments.domain.repositories.PurchaseRepository;
import lombok.AllArgsConstructor;
//Caso de uso de registro de compra
@AllArgsConstructor
@Component
public class RegisterPurchaseUseCase {

    @Autowired
    private PurchaseRepository repository;

    public PurchaseDto execute(PurchaseDto purchaseDto) {
        // Obtener el libro desde ms-buscador

        // // Validar si el libro existe, es visible y tiene stock disponible
        // if (book == null) {
        //     throw new BookNotAvailableException("El libro no existe.");
        // }
        // if (!book.getVisible()) {
        //     throw new BookNotAvailableException("El libro no está disponible para la compra.");
        // }
        // if (book.getUnitsAvaible() < purchaseDto.getQuantity()) {
        //     throw new BookNotAvailableException("No hay suficiente stock disponible.");
        // }

        // Si la validación es correcta, registrar la compra
        return this.repository.save(purchaseDto);
    }
}
