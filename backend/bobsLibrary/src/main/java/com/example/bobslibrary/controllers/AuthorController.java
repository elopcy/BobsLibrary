package com.example.bobslibrary.controllers;

import com.example.bobslibrary.entities.Author;
import com.example.bobslibrary.services.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping
    public ResponseEntity<List<Author>> getAllAuthors(){
        return ResponseEntity.ok(authorService.getAllAuthors());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id){
        return ResponseEntity.ok(authorService.getAuthorById(id));
    }

    @GetMapping("/name")
    public ResponseEntity<Author> getAuthorByName(@RequestParam String firstName, @RequestParam String lastName){
        return ResponseEntity.ok(authorService.getAuthorByName(firstName, lastName));
    }

    @PostMapping
    public ResponseEntity<Author> addBook(@Valid @RequestBody Author author){
        Author authorAdded = authorService.addAuthor(author);
        return ResponseEntity.status(HttpStatus.CREATED).body(authorAdded);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable Long id, @RequestBody Author author){
        Author updatedAuthor = authorService.updateAuthor(id, author);
        return ResponseEntity.ok(updatedAuthor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id){
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }
}
