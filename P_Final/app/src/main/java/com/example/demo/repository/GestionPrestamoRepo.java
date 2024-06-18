package com.example.demo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.domain.Prestamo;

public interface GestionPrestamoRepo extends JpaRepository<Prestamo, Long> {
    List<Prestamo> findByIsbn(String isbn);
}
