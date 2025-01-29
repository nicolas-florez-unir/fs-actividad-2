package com.grupo1.ms_buscador.controllers.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BookDto {
    private String title;
    private String author;
    private String description;
    private Boolean visible;
    private String category;
    private String publicationDate;
    private int rating;
    private int unitsAvaible;
    private double price;
}
