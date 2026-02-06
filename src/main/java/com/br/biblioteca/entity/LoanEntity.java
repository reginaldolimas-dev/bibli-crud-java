package com.br.biblioteca.entity;

import com.br.biblioteca.enums.ConditionEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Table(name = "loan")
@Data
public class LoanEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id", referencedColumnName = "id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_loan_portfolio"))
    @ToString.Exclude
    private PortfolioEntity portfolio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_loan_user"))
    @ToString.Exclude
    private UserEntity user;

    @CreationTimestamp
    @Column(name = "start_at", nullable = false, updatable = false)
    private LocalDateTime startAt;

    @Column(name = "return_at")
    private LocalDateTime returnAt;

    @Column(name = "period", nullable = false)
    private Integer period = 30;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "loan_condition")
    private ConditionEnum loanCondition;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "return_condition")
    private ConditionEnum returnCondition;


}
