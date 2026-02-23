package com.example.bobslibrary.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "book")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    @NotBlank(message = "Book's title is missing")
    @Column(nullable = false)
    private String title;

    @NotNull(message = "Book's author is missing")
    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name="author_id", nullable = false)
    private Author author;

    @NotNull(message = "Book's number of pages is missing")
    @Column(nullable = false)
    private int pagesNb;

    @NotBlank(message = "Book's ISBN is missing")
    @Column(nullable = false, unique = true)
    @Pattern(regexp = "^(97[89]\\d{10}|\\d{9}[\\dX])$",
            message = "Invalid ISBN (must be ISBN-10 or ISBN-13 is expected)")
    private String isbn;

    @NotNull(message = "Book's reading status is missing")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status readingStatus;

    @Column(name = "cover_url")
    private String coverUrl;

    @Column
    private int currentPage;

    @OneToOne(mappedBy = "book")
    private Review review;

}