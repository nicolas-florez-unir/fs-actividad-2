package com.grupo1.ms_operador.payments.domain.dtos;

//import lombok.*;

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
