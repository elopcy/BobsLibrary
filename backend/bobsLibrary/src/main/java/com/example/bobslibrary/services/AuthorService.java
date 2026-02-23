package com.example.bobslibrary.services;

import com.example.bobslibrary.entities.Author;
import com.example.bobslibrary.repositories.AuthorRepository;
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
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Transactional(readOnly = true)
    public List<Author> getAllAuthors(){
        log.info("Getting all authors from the database...");
        return authorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Author getAuthorById(Long id){
        log.info("Getting author with ID " + id + "from database...");
        return authorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "This author doesn't exist."));
    }

    @Transactional(readOnly = true)
    public Author getAuthorByName(String firstName, String lastName) {
        log.info("Getting author named " + firstName + " " + lastName);
        return authorRepository.findByFirstNameIgnoreCaseAndLastNameIgnoreCase(firstName, lastName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "This author doesn't exist."));
    }

    public Author addAuthor(Author author){
        log.info("Adding author named " + author.getFirstName() + " " + author.getLastName() + " to database...");
        return authorRepository.save(author);
    }

    public Author updateAuthor(Long id, Author updatedAuthor){
        log.info("Updating author with ID " + id + "...");
        Author existingAuthor = getAuthorById(id);
        existingAuthor.setFirstName(updatedAuthor.getFirstName());
        existingAuthor.setLastName(updatedAuthor.getLastName());
        return authorRepository.save(existingAuthor);
    }

    public void deleteAuthor(Long id){
        log.info("Deleting author with ID " + id + " from database...");
        Author authorToDelete = getAuthorById(id);
        authorRepository.delete(authorToDelete);
    }
}
