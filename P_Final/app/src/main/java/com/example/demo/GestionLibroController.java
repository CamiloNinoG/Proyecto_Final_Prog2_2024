package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

import com.example.demo.domain.Libro;
import com.example.demo.repository.GestionLibroRepo;
import com.example.demo.metodosArchivos.Metodo;

@CrossOrigin(origins = "http://localhost:8085")
@RestController
@RequestMapping("/api")
public class GestionLibroController {

    Metodo metodo = new Metodo();

    @Autowired
    GestionLibroRepo gestionLibroRepository;

    @GetMapping("/libross")
    public ResponseEntity<List<Libro>> getAllLibros(@RequestParam(required = false) String title) {
        try {
            List<Libro> libros = new ArrayList<>();

            if (title == null) {
                libros.addAll(gestionLibroRepository.findAll());
            } else {
                libros.addAll(gestionLibroRepository.findByTitle(title));
            }

            return new ResponseEntity<>(libros, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/libross/{id}")
    public ResponseEntity<Libro> getLibroById(@PathVariable("id") Long id) {
        Optional<Libro> libroData = gestionLibroRepository.findById(id);

        if (libroData.isPresent()) {
            return new ResponseEntity<>(libroData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

   @PostMapping("/libross")
    public ResponseEntity<Libro> createLibro(@RequestBody Libro libro) {
        try {
            Libro _libro = gestionLibroRepository.save(new Libro(libro.getId(), libro.getTitle(), libro.getAutor(), libro.getGender(), libro.getDate(), libro.getIsbn(), libro.getStock(), libro.getDescription()));
            metodo.agregarLibro("app/src/main/java/com/example/demo/archivos/libros.txt", _libro);          
            return new ResponseEntity<>(_libro, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);


        }
    }

    @PutMapping("/libross/{id}")
    public ResponseEntity<Libro> updateLibro(@PathVariable("id") Long id, @RequestBody Libro libro) {
        Optional<Libro> libroData = gestionLibroRepository.findById(id);

        if (libroData.isPresent()) {
            Libro _libro = libroData.get();
            _libro.setTitle(libro.getTitle());
            _libro.setAutor(libro.getAutor());
            _libro.setGender(libro.getGender());
            _libro.setDate(libro.getDate());
            _libro.setIsbn(libro.getIsbn());
            _libro.setStock(libro.getStock());
            _libro.setDescription(libro.getDescription());
            gestionLibroRepository.save(_libro);
            metodo.actualizarLibro("app/src/main/java/com/example/demo/archivos/libros.txt", id, _libro);
            return new ResponseEntity<>(_libro, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/libross/{id}")
    public ResponseEntity<HttpStatus> deleteLibroById(@PathVariable("id") Long id) {
        try {
            Optional<Libro> libroOptional = gestionLibroRepository.findById(id);
            
            if (libroOptional.isPresent()) {
                Libro libro = libroOptional.get();
                gestionLibroRepository.delete(libro);
                metodo.eliminarLibro("app/src/main/java/com/example/demo/archivos/libros.txt", id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

   

}
