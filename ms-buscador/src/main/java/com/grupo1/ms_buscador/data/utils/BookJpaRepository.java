package com.grupo1.ms_buscador.data.utils;

import java.util.List;
import com.grupo1.ms_buscador.data.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

interface BookJpaRepository extends JpaRepository<BookEntity, Long>, JpaSpecificationExecutor<BookEntity> {

    List<BookEntity> findByTitle(String title);

    List<BookEntity> findByAuthor(String author);

    List<BookEntity> findByVisible(Boolean visible);

    List<BookEntity> findByTitleAndAuthor(String title, String author);

}

