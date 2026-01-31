package com.br.biblioteca.entity;

import com.br.biblioteca.enums.GenreEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "genre", indexes = {
        @Index(name = "idx_genre_book_id", columnList = "book_id")
})
@Data
@NoArgsConstructor
public class BookGenreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", referencedColumnName = "isbn", nullable = false,
            foreignKey = @ForeignKey(name = "fk_genre_book"))
    @ToString.Exclude
    private BookEntity book;

    @Enumerated(EnumType.STRING)
    @Column(name = "genre", nullable = false)
    private GenreEnum genre;

    public BookGenreEntity(BookEntity book, GenreEnum genre) {
        this.book = book;
        this.genre = genre;
    }
}
