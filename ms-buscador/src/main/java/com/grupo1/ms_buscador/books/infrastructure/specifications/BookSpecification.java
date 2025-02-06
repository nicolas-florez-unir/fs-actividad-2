package com.grupo1.ms_buscador.books.infrastructure.specifications;

import org.springframework.data.jpa.domain.Specification;

import com.grupo1.ms_buscador.books.domain.dtos.SearchBookFiltersDto;
import com.grupo1.ms_buscador.books.infrastructure.entities.BookEntity;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class BookSpecification {

    public static Specification<BookEntity> filterBooks(SearchBookFiltersDto filters) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(filters.getIds() != null && !filters.getIds().isEmpty()) {
                predicates.add(root.get("id").in(filters.getIds()));
            }

            if (filters.getTitle() != null && !filters.getTitle().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")),
                        "%" + filters.getTitle().toLowerCase() + "%"));
            }

            if (filters.getAuthor() != null && !filters.getAuthor().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("author")),
                        "%" + filters.getAuthor().toLowerCase() + "%"));
            }

            if (filters.getDescription() != null && !filters.getDescription().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("description")),
                        "%" + filters.getDescription().toLowerCase() + "%"));
            }
            
            if (filters.getPublicationDate() != null && !filters.getPublicationDate().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("publicationDate"), filters.getPublicationDate()));
            }

            if (filters.getCategory() != null && !filters.getCategory().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("category"), filters.getCategory()));
            }

            if (filters.getVisible() != null) {
                predicates.add(criteriaBuilder.equal(root.get("visible"), filters.getVisible()));
            }

            if(filters.getIsbnCode() != null && !filters.getIsbnCode().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("isbnCode")),
                        "%" + filters.getIsbnCode().toLowerCase() + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
