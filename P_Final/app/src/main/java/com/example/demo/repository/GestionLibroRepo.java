package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.domain.Libro;

public interface GestionLibroRepo extends JpaRepository<Libro, Long> {
    List<Libro> findByTitle(String title);
    List<Libro> findByIsbn(String isbn);
}
