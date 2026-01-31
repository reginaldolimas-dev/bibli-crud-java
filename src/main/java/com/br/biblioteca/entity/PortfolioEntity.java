package com.br.biblioteca.entity;

import com.br.biblioteca.enums.ConditionEnum;
import com.br.biblioteca.enums.CoverEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "portfolio")
@Data
public class PortfolioEntity {
    @Id
    @Column(name = "id", length = 26, nullable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", referencedColumnName = "isbn", nullable = false,
            foreignKey = @ForeignKey(name = "fk_portfolio_book"))
    @ToString.Exclude
    private BookEntity book;

    @Enumerated(EnumType.STRING)
    @Column(name = "condition")
    private ConditionEnum condition;

    @Enumerated(EnumType.STRING)
    @Column(name = "cover")
    private CoverEnum cover;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<LoanEntity> loans = new ArrayList<>();
}
