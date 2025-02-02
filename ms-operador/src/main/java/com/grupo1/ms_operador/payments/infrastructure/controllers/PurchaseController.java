package com.grupo1.ms_operador.payments.infrastructure.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import com.grupo1.ms_operador.payments.application.RegisterPurchaseUseCase;
import com.grupo1.ms_operador.payments.domain.dtos.PurchaseDto;
import com.grupo1.ms_operador.payments.domain.exceptions.BookNotAvailableException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo1.ms_operador.core.infrastructure.clients.BuscadorClient;
import com.grupo1.ms_operador.payments.application.GetAllPurchasesUseCase;
import com.grupo1.ms_operador.payments.application.GetPurchaseByIdUseCase;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/purchases")
public class PurchaseController {

    @Autowired
    private BuscadorClient buscadorClient;

    @Autowired
    private RegisterPurchaseUseCase registerPurchaseUseCase;

    @Autowired
    private GetPurchaseByIdUseCase getPurchaseByIdUseCase;

    @Autowired
    private GetAllPurchasesUseCase getAllPurchasesUseCase;

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
        return ResponseEntity.ok().body(getPurchaseByIdUseCase.execute(id));
    }

    @GetMapping
    public ResponseEntity<?> getAllPurchases() {
        return ResponseEntity.ok().body(getAllPurchasesUseCase.execute());
    }

    @GetMapping("/books/{id}")
    public Object getPurchasesByBookId(@PathVariable("id") Long id) {
        return this.buscadorClient.getBookById(id);
    }
}
