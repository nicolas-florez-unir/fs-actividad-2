package com.grupo1.ms_operador.payments.domain.dtos;

//import lombok.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PurchaseDto {
    private Long id;
    private Long userId;
    private Long bookId;
    private int quantity;
    private double totalPrice;
    private String purchaseDate;
}
