package com.example.bobslibrary.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "author")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authorId;

    @NotBlank(message = "Author's first name is missing")
    @Column(nullable = false)
    private String firstName;

    @NotBlank(message = "Author's last name is missing")
    @Column(nullable = false)
    private String lastName;

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
