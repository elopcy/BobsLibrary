package com.example.bobslibrary.services;

import com.example.bobslibrary.entities.Author;
import com.example.bobslibrary.entities.Book;
import com.example.bobslibrary.entities.Review;
import com.example.bobslibrary.entities.Status;
import com.example.bobslibrary.repositories.AuthorRepository;
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
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Transactional(readOnly = true)
    public List<Book> getAllBooks(){
        log.info("Getting all books from the database...");
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Book> getBooksByTitle(String title){
        log.info("Getting all books containing " + title + " in their title");
        return bookRepository.findByTitleIgnoreCaseContaining(title);
    }

    @Transactional(readOnly = true)
    public List<Book> getBooksByAuthor(Author author){
        log.info("Getting all books written by " + author.getFirstName() + " " + author.getLastName());
        return bookRepository.findByAuthor(author);
    }

    @Transactional(readOnly = true)
    public List<Book> getBooksByReadingStatus(Status readingStatus){
        log.info("Getting all books with the status: " + readingStatus.toString());
        return bookRepository.findByReadingStatus(readingStatus);
    }

    @Transactional(readOnly = true)
    public Book getBookById(Long id){
        log.info("Getting book with ID " + id + "from database...");
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "This book doesn't exist."));
    }

    @Transactional(readOnly = true)
    public Book getBookByIsbn(String isbn){
        log.info("Getting book with isbn: " + isbn);
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "This book doesn't exist"));
    }

    @Transactional(readOnly = true)
    public Book getBookByTitleAndAuthor(String title, Author author){
        log.info("Getting " + title + " written by " + author.toString());
        return bookRepository.findByTitleIgnoreCaseAndAuthor(title, author)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "This book doesn't exist."));
    }

    public Book addBook(Book book){
        log.info("Adding book named " + book.getTitle() + " to database...");

        // Checks if the author is already in the database
        // New author is created otherwise
        Author author = authorRepository.findByFirstNameIgnoreCaseAndLastNameIgnoreCase(book.getAuthor().getFirstName(), book.getAuthor().getLastName())
                        .orElseGet(() -> {
                            Author newAuthor = new Author();
                            newAuthor.setFirstName(book.getAuthor().getFirstName());
                            newAuthor.setLastName(book.getAuthor().getLastName());
                            return authorRepository.save(newAuthor);
                        });
        book.setAuthor(author);

        // Create an empty review
        Review review = new Review();
        review.setBook(book);

        Book savedBook = bookRepository.save(book);
        reviewRepository.save(review);

        return savedBook;
    }

    public Book updateBookTitleById(Long id, String title){
        log.info("Updating book title with ID " + id + "...");
        Book existingBook = getBookById(id);
        existingBook.setTitle(title);
        return bookRepository.save(existingBook);
    }

    public Book updateBookStatus(Long id, Status newReadingStatus){
        log.info("Updating book's status to " + newReadingStatus.toString() + " (ID=" + id + ")");
        Book existingBook = getBookById(id);
        existingBook.setReadingStatus(newReadingStatus);
        return bookRepository.save(existingBook);
    }

    public Book updateBookProgress(Long id, int newCurrentPage){
        log.info("Updating book progress: now on page " + newCurrentPage + " (ID=" + id + ")");
        Book existingBook = getBookById(id);
        existingBook.setCurrentPage(newCurrentPage);

        if (newCurrentPage >= existingBook.getPagesNb()){
            existingBook.setReadingStatus(Status.READ);
        }

        return bookRepository.save(existingBook);
    }

    public void deleteBook(Long id){
        log.info("Deleting book with ID " + id + " from database...");
        Book bookToDelete = getBookById(id);
        bookRepository.delete(bookToDelete);
    }

    public void deleteBookByTitleAndAuthor(String title, String firstName, String lastName){
        log.info("Deleting " + title + " written by " + firstName + " " + lastName + " from database...");
        Author author = authorRepository.findByFirstNameIgnoreCaseAndLastNameIgnoreCase(firstName, lastName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "This author doesn't exist."));
        bookRepository.deleteByTitleIgnoreCaseAndAuthor(title, author);
    }

}
