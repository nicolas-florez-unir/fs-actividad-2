package com.grupo1.ms_buscador.books.domain.dtos;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Builder
public class SearchBookFiltersDto {
    private List<Long> ids;
    private String title;
    private String author;
    private String description;
    private String publicationDate;
    private String category;
    private String isbnCode;
    private Boolean visible;
}
