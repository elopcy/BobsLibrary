package com.example.bobslibrary.repositories;

import com.example.bobslibrary.entities.Author;
import com.example.bobslibrary.entities.Book;
import com.example.bobslibrary.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    public List<Book> findByTitleIgnoreCaseContaining(String title);
    public Optional<Book> findByIsbn(String isbn);
    public List<Book> findByAuthor(Author author);
    public Optional<Book> findByTitleIgnoreCaseAndAuthor(String title, Author author);
    public void deleteByTitleIgnoreCaseAndAuthor(String title, Author author);
    public List<Book> findByReadingStatus(Status readingStatus);
}
