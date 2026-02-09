package com.br.biblioteca.repository;

import com.br.biblioteca.dto.BookFilterDTO;
import com.br.biblioteca.dto.projection.BookSummaryDTO;
import com.br.biblioteca.entity.BookEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, String> {
    @Query("""
            SELECT b.isbn AS isbn,
                   b.title AS title,
                   b.author AS author,
                   b.releaseYear AS releaseYear
            FROM BookEntity b
            WHERE (:#{#dto.isbn} IS NULL OR b.isbn = :#{#dto.isbn})
            AND (:#{#dto.title} IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :#{#dto.title}, '%')))
            AND (:#{#dto.author} IS NULL OR LOWER(b.author) LIKE LOWER(CONCAT('%', :#{#dto.author}, '%')))
            AND b.active = true
            """)
    List<BookSummaryDTO> findByResume(BookFilterDTO dto, Pageable pageable);

    @Query(
            value = """
        SELECT b.isbn AS isbn,
               b.title AS title,
               b.author AS author,
               b.release_year AS releaseYear
        FROM books b 
        WHERE (:isbn IS NULL OR b.isbn = :isbn)
        AND (:title IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%')))
        AND (:author IS NULL OR LOWER(b.author) LIKE LOWER(CONCAT('%', :author, '%')))
        AND b.active = true
        """,
        nativeQuery = true
    )
    List<BookSummaryDTO> findByResumeQueryNative(@Param("isbn") String isbn,
                                                 @Param("title") String title,
                                                 @Param("author") String author,
                                                 Pageable pageable);

    boolean existsByIsbn(String isbn);
}
