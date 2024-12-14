package com.readview.book_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "genre")
    private String genre;

    @Column(name = "published_date")
    private LocalDate publishedDate;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "language")
    private String language;

    @Column(name = "page_count")
    private int pageCount;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "summary")
    private String summary;
}
