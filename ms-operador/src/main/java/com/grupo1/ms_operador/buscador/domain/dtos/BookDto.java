package com.grupo1.ms_operador.buscador.domain.dtos;

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