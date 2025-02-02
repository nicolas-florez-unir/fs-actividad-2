package com.grupo1.ms_buscador.books.infrastructure.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
@Setter
public class SearchBookRequest {
    private String title;
    private String author;
    private String description;
    private String publicationDate;
    private String category;
    private String isbnCode;
    private Boolean visible;
    private String sbnCode;
}
