package com.grupo1.ms_operador.buscador.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookDto {
    private Long id;
    private String title;
    private String author;
    private String description;
    private Boolean visible;
    private String category;
    private String isbnCode;
    private String publicationDate;
    private int rating;
    private int unitsAvaible;
    private double price;
}