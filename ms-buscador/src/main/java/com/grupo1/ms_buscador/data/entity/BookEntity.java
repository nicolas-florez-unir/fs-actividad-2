package com.grupo1.ms_buscador.data.Entity;

import ms_buscador_books.books.controller.model.BookDto;
import ms_buscador_books.books.data.utils.Consts;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@Table(name = "BookEntity")
@Entity
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = Consts.TITLE, unique = true)
    private String title;

    @Column(name = Consts.AUTHOR)
    private String author;

    @Column(name = Consts.DESCRIPTION)
    private String description;

    @Column(name = Consts.VISIBLE)
    private Boolean visible;

    @Column(name = Consts.CATEGORY)
    private String category;

    @Column(name = Consts.PUBLICATION_DATE)
    private String publicationDate;

    @Column(name = Consts.RATING)
    private int rating;

    @Column(name = Consts.UNITS_AVAIBLE)
    private int unitsAvaible;

    @Column(name = Consts.PRICE)
    private double price;


    public void update(BookDto bookDto) {
        this.title = bookDto.getTitle();
        this.author = bookDto.getAuthor();
        this.description = bookDto.getDescription();
        this.visible = bookDto.getVisible();
        this.category = bookDto.getCategory();
        this.publicationDate = bookDto.getPublicationDate();
        this.rating = bookDto.getRating();
        this.unitsAvaible = bookDto.getUnitsAvaible();
        this.price = bookDto.getPrice();

    }
}
