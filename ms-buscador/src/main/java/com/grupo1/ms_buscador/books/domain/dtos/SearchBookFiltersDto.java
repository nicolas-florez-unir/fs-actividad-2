package com.grupo1.ms_buscador.books.domain.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Builder
public class SearchBookFiltersDto {
    private String title;
    private String author;
    private String description;
    private String publicationDate;
    private String category;
    private String isbnCode;
    private Boolean visible;
}
