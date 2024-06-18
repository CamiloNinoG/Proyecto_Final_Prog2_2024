package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.demo.domain.Libro;
import com.example.demo.domain.Prestamo;
import com.example.demo.domain.Usuario;
import com.example.demo.metodosArchivos.Metodo;
import com.example.demo.repository.GestionLibroRepo;
import com.example.demo.repository.GestionPrestamoRepo;
import com.example.demo.repository.GestionUsuarioRepo;

import java.util.ArrayList;

@SpringBootApplication
public class AppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(GestionLibroRepo libroRepo, GestionUsuarioRepo usuarioRepo, GestionPrestamoRepo prestamoRepo) {
        return (args) -> {

            Metodo metodo = new Metodo();
            ArrayList<Libro> libros = new ArrayList<>();
            ArrayList<Usuario> usuarios = new ArrayList<>();
            ArrayList<Prestamo> prestamos = new ArrayList<>();

            metodo.cargarLibros(libros, "app/src/main/java/com/example/demo/archivos/libros.txt ");
            metodo.cargarUsuarios(usuarios, "app/src/main/java/com/example/demo/archivos/usuarios.txt");
            metodo.cargarPrestamos(prestamos, "app/src/main/java/com/example/demo/archivos/prestamos.txt", libros, usuarios);

            // Guardar los datos en la base de datos
            libroRepo.saveAll(libros);
            usuarioRepo.saveAll(usuarios);
            prestamoRepo.saveAll(prestamos);
        };
    }
}
