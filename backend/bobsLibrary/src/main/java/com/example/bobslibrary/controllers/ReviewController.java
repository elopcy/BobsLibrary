package com.example.bobslibrary.controllers;

import com.example.bobslibrary.entities.Book;
import com.example.bobslibrary.entities.Review;
import com.example.bobslibrary.services.BookService;
import com.example.bobslibrary.services.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "http://localhost:4200")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(){
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long reviewId){
        return ResponseEntity.ok(reviewService.getReviewById(reviewId));
    }

    @GetMapping("/forbook/{bookId}")
    public ResponseEntity<Review> getReviewByBook(@PathVariable Long bookId){
        Book existingBook = bookService.getBookById(bookId);
        return ResponseEntity.ok(reviewService.getReviewByBook(existingBook));
    }

    @PostMapping
    public ResponseEntity<Review> addBook(@Valid @RequestBody Review review){
        Review addedReview = reviewService.addReview(review);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedReview);
    }

    @PutMapping("{reviewId}/rating")
    public ResponseEntity<Review> updateReviewRating(@PathVariable Long reviewId, @RequestParam int rating){
        Review updatedReview = reviewService.updateRating(reviewId, rating);
        return ResponseEntity.ok(updatedReview);
    }

    @PutMapping("{reviewId}/comment")
    public ResponseEntity<Review> updateReviewComment(@PathVariable Long reviewId, @Valid @RequestBody String comment){
        Review updatedReview = reviewService.updateComment(reviewId, comment);
        return ResponseEntity.ok(updatedReview);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId){
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }
}
