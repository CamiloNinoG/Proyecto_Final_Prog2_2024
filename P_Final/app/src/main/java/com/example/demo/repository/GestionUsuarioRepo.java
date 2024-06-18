package com.example.demo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.domain.Usuario;

public interface GestionUsuarioRepo extends JpaRepository<Usuario, Long> {
        List<Usuario> findByName(String name);
}
