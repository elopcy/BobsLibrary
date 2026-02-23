package com.example.bobslibrary.repositories;

import com.example.bobslibrary.entities.Book;
import com.example.bobslibrary.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    public Optional<Review> findByBook(Book book);
}
