package com.br.biblioteca.repository;

import com.br.biblioteca.dto.PortfolioFilterDTO;
import com.br.biblioteca.dto.projection.PortfolioSummaryDTO;
import com.br.biblioteca.entity.PortfolioEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortfolioRepository extends JpaRepository<PortfolioEntity, String> {
    @Query("""
            SELECT p.id AS id,
                   p.book.isbn AS bookIsbn,
                   p.book.title AS bookTitle,
                   p.book.author AS bookAuthor,
                   p.condition AS condition,
                   p.cover AS cover
            FROM PortfolioEntity p
            WHERE (:#{#dto.id} IS NULL OR p.id = :#{#dto.id})
            AND (:#{#dto.bookIsbn} IS NULL OR p.book.isbn = :#{#dto.bookIsbn})
            AND p.active = true
            """)
    List<PortfolioSummaryDTO> findByResume(PortfolioFilterDTO dto, Pageable pageable);
}
