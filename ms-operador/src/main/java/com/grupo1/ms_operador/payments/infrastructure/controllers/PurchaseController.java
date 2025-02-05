package com.grupo1.ms_operador.payments.infrastructure.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import com.grupo1.ms_operador.payments.application.RegisterPurchaseUseCase;
import com.grupo1.ms_operador.payments.domain.dtos.PurchaseDto;
import com.grupo1.ms_operador.payments.domain.exceptions.BookNotAvailableException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo1.ms_operador.core.infrastructure.clients.BuscadorClient;
import com.grupo1.ms_operador.payments.application.GetAllPurchasesUseCase;
import com.grupo1.ms_operador.payments.application.GetPurchaseByIdUseCase;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/purchases")
@Tag(name = "Purchases", description = "API for managing book's purchases")
public class PurchaseController {

    @Autowired
    private BuscadorClient buscadorClient;

    @Autowired
    private RegisterPurchaseUseCase registerPurchaseUseCase;

    @Autowired
    private GetPurchaseByIdUseCase getPurchaseByIdUseCase;

    @Autowired
    private GetAllPurchasesUseCase getAllPurchasesUseCase;

    @Operation(summary = "Create a new purchase", description = "Endpoint to create a new purchase")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Purchase created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<?> createPurchase(@RequestBody(description = "Purchase data", required = true) PurchaseDto purchaseDto) {
        try {
            return ResponseEntity.ok(registerPurchaseUseCase.execute(purchaseDto));
        } catch (BookNotAvailableException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error processing the purchase.");
        }
    }

    @Operation(summary = "Get purchase by ID", description = "Endpoint to get a purchase by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Purchase found"),
            @ApiResponse(responseCode = "404", description = "Purchase not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PurchaseDto> getPurchaseById(@Parameter(description = "ID to obtain purchase", required = true) @PathVariable Long id) {
        return ResponseEntity.ok().body(getPurchaseByIdUseCase.execute(id));
    }

    @Operation(summary = "Get all book's purchases", description = "Endpoint to get all purchases")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Purchases obtain successfully")
    })
    @GetMapping
    public ResponseEntity<?> getAllPurchases() {
        return ResponseEntity.ok().body(getAllPurchasesUseCase.execute());
    }

    @Operation(summary = "Get purchases by book ID", description = "Endpoint to get purchases by book ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Purchases retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    @GetMapping("/books/{id}")
    public Object getPurchasesByBookId(@Parameter(description = "ID of the book to obtain purchases for", required = true) @PathVariable("id") Long id) {
        return this.buscadorClient.getBookById(id);
    }
}