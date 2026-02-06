package com.br.biblioteca.repository;

import com.br.biblioteca.dto.LoanFilterDTO;
import com.br.biblioteca.dto.LoanResponseDTO;
import com.br.biblioteca.dto.projection.LoanSummaryDTO;
import com.br.biblioteca.entity.LoanEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<LoanEntity, String> {
    @Query("""
            SELECT l.id AS id,
                   l.portfolio.id AS portfolioId,
                   l.portfolio.book.title AS bookTitle,
                   l.portfolio.book.author AS bookAuthor,
                   l.user.id AS userId,
                   l.user.name AS userName,
                   l.startAt AS startAt,
                   l.returnAt AS returnAt,
                   l.period AS period,
                   l.loanCondition AS loanCondition,
                   l.returnCondition AS returnCondition
            FROM LoanEntity l
            WHERE (:#{#dto.userId} IS NULL OR l.user.id = :#{#dto.userId})
            AND (:#{#dto.portfolioId} IS NULL OR l.portfolio.id = :#{#dto.portfolioId})
            AND (:#{#dto.bookId} IS NULL OR l.portfolio.book.isbn = :#{#dto.bookId})
            AND l.returnAt IS NULL
            ORDER BY l.startAt DESC
            """)
    List<LoanSummaryDTO> findByResume(LoanFilterDTO dto, Pageable pageable);

    @Query(
            """
        SELECT COUNT(1) > 0
        FROM LoanEntity l
        WHERE l.portfolio.id = :portfolioId
        AND l.returnAt IS NULL
        """
    )
    boolean existsActiveLoanByPortfolioId(@Param("portfolioId") String portfolioId);

    @Query("""
            SELECT l FROM LoanEntity l
            LEFT JOIN FETCH l.portfolio p
            WHERE p.id = :portfolioId
            AND l.returnAt IS NULL
            """)
    Optional<LoanEntity> findActiveByPortfolioId(String portfolioId);

    @Query("""
    SELECT
        l.id AS id,
        l.startAt AS startAt,
        l.period AS period,
        l.loanCondition AS loanCondition,

        p.id AS portfolioId,
        b.title AS bookTitle,

        u.id AS userId,
        u.name AS userName
    FROM LoanEntity l
    LEFT JOIN l.portfolio p
    LEFT JOIN p.book b
    LEFT JOIN l.user u
    WHERE l.id = :loanId
""")
    Optional<LoanResponseDTO> findProjectionById(
            @Param("loanId") String loanId
    );


    @Query(
            """
    SELECT COUNT(1) > 0
        FROM LoanEntity l
        WHERE l.user.id = :userId
        AND l.returnAt is null 
        """
    )
    boolean existsByUserId(String userId);

}
