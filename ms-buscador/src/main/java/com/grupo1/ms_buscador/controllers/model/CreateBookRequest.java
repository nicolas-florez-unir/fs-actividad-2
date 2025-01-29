package com.grupo1.ms_buscador.controllers.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookRequest {
    private String title;
    private String author;
    private String description;
    private Boolean visible;
    private String category;
    private String publicationDate;
    private int rating;

}
