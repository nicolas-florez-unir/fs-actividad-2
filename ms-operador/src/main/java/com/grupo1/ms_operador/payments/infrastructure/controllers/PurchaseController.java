package com.grupo1.ms_operador.payments.infrastructure.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.grupo1.ms_operador.payments.application.RegisterPurchaseUseCase;
import com.grupo1.ms_operador.payments.domain.dtos.PurchaseDto;
import com.grupo1.ms_operador.payments.domain.exceptions.BookNotAvailableException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/purchases")
public class PurchaseController {

    @Autowired
    private RegisterPurchaseUseCase registerPurchaseUseCase;

    @PostMapping
    public ResponseEntity<?> createPurchase(@RequestBody PurchaseDto purchaseDto) {
        try {
            return ResponseEntity.ok(registerPurchaseUseCase.execute(purchaseDto));
        } catch (BookNotAvailableException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al procesar la compra.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseDto> getPurchaseById(@PathVariable Long id) {
        return ResponseEntity.ok().body(registerPurchaseUseCase.getById(id));
    }

    @GetMapping
    public ResponseEntity<?> getAllPurchases() {
        return ResponseEntity.ok().body(registerPurchaseUseCase.getAll());
    }
}
