package com.example.bobslibrary.services;

import com.example.bobslibrary.entities.Author;
import com.example.bobslibrary.entities.Book;
import com.example.bobslibrary.entities.Review;
import com.example.bobslibrary.repositories.BookRepository;
import com.example.bobslibrary.repositories.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
@Slf4j
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private BookRepository bookRepository;

    @Transactional(readOnly = true)
    public List<Review> getAllReviews(){
        log.info("Getting all reviews from the database...");
        return reviewRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Review getReviewById(Long id){
        log.info("Getting review with ID " + id + "from database...");
        return reviewRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "This review doesn't exist."));
    }

    @Transactional(readOnly = true)
    public Review getReviewByBook(Book book){
        log.info("Getting review made for book named " + book.getTitle());
        return reviewRepository.findByBook(book)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "This review doesn't exist."));
    }

    public Review addReview(Review review){
        log.info("Adding review to database...");
        return reviewRepository.save(review);
    }

    public Review updateReview(Long id, Review updatedReview){
        log.info("Updating review with ID " + id + "...");
        Review existingReview = getReviewById(id);
        existingReview.setRating(updatedReview.getRating());
        existingReview.setComment(updatedReview.getComment());
        return reviewRepository.save(existingReview);
    }

    public Review updateRating(Long id, int rating){
        log.info("Updating rating of review with ID " + id + "...");
        Review existingReview = getReviewById(id);
        existingReview.setRating(rating);
        return reviewRepository.save(existingReview);
    }

    public Review updateComment(Long id, String comment){
        log.info("Updating comment of review with ID " + id + "...");
        Review existingReview = getReviewById(id);
        existingReview.setComment(comment);
        return reviewRepository.save(existingReview);
    }

    public void deleteReview(Long id){
        log.info("Deleting review with ID " + id + " from database...");
        Review reviewToDelete = getReviewById(id);
        reviewRepository.delete(reviewToDelete);
    }

}
