package com.example.bobslibrary.controllers;

import com.example.bobslibrary.entities.Author;
import com.example.bobslibrary.entities.Book;
import com.example.bobslibrary.entities.Status;
import com.example.bobslibrary.services.BookService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "http://localhost:4200")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks(){
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/status/{readingStatus}")
    public ResponseEntity<List<Book>> getBooksByReadingStatus(@PathVariable Status readingStatus){
        return ResponseEntity.ok(bookService.getBooksByReadingStatus(readingStatus));
    }

    @GetMapping("/search/title")
    public ResponseEntity<List<Book>> getBooksByTitle(@RequestParam String title){
        return ResponseEntity.ok(bookService.getBooksByTitle(title));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id){
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @GetMapping("/search/isbn")
    public ResponseEntity<Book> getBookByIsbn(@RequestParam String isbn){
        return ResponseEntity.ok(bookService.getBookByIsbn(isbn));
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@Valid @RequestBody Book book){
        Book bookAdded = bookService.addBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookAdded);
    }

    @PutMapping("/update/title/{id}")
    public ResponseEntity<Book> updateBookTitle(@PathVariable Long id, @RequestParam String title){
        Book updatedBook = bookService.updateBookTitleById(id, title);
        return ResponseEntity.ok(updatedBook);
    }

    @PutMapping("/update/progress/{id}")
    public ResponseEntity<Book> updateBookProgress(@PathVariable Long id, @RequestParam int newCurrentPage){
        Book updatedBook = bookService.updateBookProgress(id, newCurrentPage);
        return ResponseEntity.ok(updatedBook);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteBookByTitleAndAuthor(@RequestParam String title, @RequestParam String firstName, @RequestParam String lastName){
        bookService.deleteBookByTitleAndAuthor(title, firstName, lastName);
        return ResponseEntity.noContent().build();
    }
}
