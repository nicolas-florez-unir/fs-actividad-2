package com.grupo1.ms_buscador.books.infrastructure.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "books")
@Entity
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "description")
    private String description;

    @Column(name = "visible")
    private boolean visible;

    @Column(name = "category")
    private String category;

    @Column(name = "isbn_code")
    private String isbnCode;

    @Column(name = "publication_date")
    private String publicationDate;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "units_available")
    private Integer unitsAvaible;

    @Column(name = "price")
    private double price;

}