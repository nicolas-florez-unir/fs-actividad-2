package com.grupo1.ms_buscador.data.utils;

import lombok.RequiredArgsConstructor;
import com.grupo1.ms_buscador.data.utils.SearchOperation;
import org.springframework.stereotype.Repository;
import com.grupo1.ms_buscador.data.utils.*;
import com.grupo1.ms_buscador.data.entity.BookEntity;


import java.util.List;
import org.apache.commons.lang3.StringUtils;

@Repository
@RequiredArgsConstructor
public class BookRepository {

    private final BookJpaRepository repository;

    public List<BookEntity> getBooks() {
        return repository.findAll();
    }

    public BookEntity getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public BookEntity save(BookEntity book) {
        return repository.save(book);
    }

    public void delete(BookEntity book) {
        repository.delete(book);
    }

    public List<BookEntity> search(String title, String author, String description, Boolean visible, String category,
                             String publicationDate, Integer rating, Double price, Integer unitsAvaible) {
        SearchCriteria<BookEntity> spec = new SearchCriteria<>();

        if (StringUtils.isNotBlank(title)) {
            spec.add(new SearchStatement(Consts.TITLE, title, SearchOperation.MATCH));
        }

        if (StringUtils.isNotBlank(author)) {
            spec.add(new SearchStatement(Consts.AUTHOR, author, SearchOperation.MATCH));
        }

        if (StringUtils.isNotBlank(description)) {
            spec.add(new SearchStatement(Consts.DESCRIPTION, description, SearchOperation.MATCH));
        }

        if (visible != null) {
            spec.add(new SearchStatement(Consts.VISIBLE, visible, SearchOperation.EQUAL));
        }

        if (StringUtils.isNotBlank(category)) {
            spec.add(new SearchStatement(Consts.CATEGORY, category, SearchOperation.MATCH));
        }

        if (StringUtils.isNotBlank(publicationDate)) {
            spec.add(new SearchStatement(Consts.PUBLICATION_DATE, publicationDate, SearchOperation.EQUAL));
        }

        if (rating != 0) {
            spec.add(new SearchStatement(Consts.RATING, rating, SearchOperation.EQUAL));
        }

        if (price != 0) {
            spec.add(new SearchStatement(Consts.PRICE, price, SearchOperation.EQUAL));
        }

        if (unitsAvaible != 0) {
            spec.add(new SearchStatement(Consts.UNITS_AVAIBLE, unitsAvaible, SearchOperation.GREATER_THAN_EQUAL));
        }



        return repository.findAll(spec);
    }


}
