package com.grupo1.ms_buscador.books.infrastructure.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class CreateBookRequest {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Author is required")
    private String author;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Visible is required")
    private Boolean visible;

    @NotBlank(message = "Category is required")
    private String category;

    @NotBlank(message = "Category is required")
    private String isbnCode;
    
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Publication date must be in the format YYYY-MM-DD")
    private String publicationDate;

    @Min(value = 0, message = "Rating must be a positive number")
    @Max(value = 5, message = "Rating must be a number between 0 and 5")
    private int rating;

    @Min(value = 0, message = "Units avaible must be a positive number")
    private int unitsAvaible;

    @Min(value = 0, message = "Price must be a positive number")
    private double price;
}
