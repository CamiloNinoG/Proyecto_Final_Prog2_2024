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

import com.example.demo.domain.Prestamo;
import com.example.demo.repository.GestionPrestamoRepo;
import com.example.demo.metodosArchivos.Metodo;

@CrossOrigin(origins = "http://localhost:8085")
@RestController
@RequestMapping("/api")
public class GestionPrestamoController {

    Metodo metodo = new Metodo();

    @Autowired
    GestionPrestamoRepo gestionPrestamRepo;

    @GetMapping("/prestamos")
    public ResponseEntity<List<Prestamo>> getAllPrestamos(@RequestParam(required = false) String isbn) {
        try {
            List<Prestamo> prestamos = new ArrayList<>();

            if (isbn == null) {
                prestamos.addAll(gestionPrestamRepo.findAll());
            } else {
                prestamos.addAll(gestionPrestamRepo.findByIsbn(isbn));
            }

            return new ResponseEntity<>(prestamos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/prestamos/{id}")
    public ResponseEntity<Prestamo> getPrestamoById(@PathVariable("id") Long id) {
        Optional<Prestamo> PrestamoData = gestionPrestamRepo.findById(id);

        if (PrestamoData.isPresent()) {
            return new ResponseEntity<>(PrestamoData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/prestamos")
    public ResponseEntity<Prestamo> createPrestamo(@RequestBody Prestamo Prestamo) {
        try {
            Prestamo _prestamo = gestionPrestamRepo.save(Prestamo);
            metodo.agergarPrestamo("app/src/main/java/com/example/demo/archivos/prestamos.txt", _prestamo);
            return new ResponseEntity<>(_prestamo, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PutMapping("/prestamos/{id}")
    public ResponseEntity<Prestamo> updatePrestamo(@PathVariable("id") Long id, @RequestBody Prestamo Prestamo) {
        Optional<Prestamo> PrestamoData = gestionPrestamRepo.findById(id);

        if (PrestamoData.isPresent()) {
            Prestamo _Prestamo = PrestamoData.get();
            _Prestamo.setIsbn(Prestamo.getIsbn());
            _Prestamo.setId_user(Prestamo.getId_user());
            _Prestamo.setDate(Prestamo.getDate());
            gestionPrestamRepo.save(_Prestamo);
            metodo.actualizarPrestamo("app/src/main/java/com/example/demo/archivos/prestamos.txt", id, _Prestamo);
            return new ResponseEntity<>(_Prestamo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/prestamos/{id}")
    public ResponseEntity<HttpStatus> deletePrestamoById(@PathVariable("id") Long id) {
        try {
            Optional<Prestamo> PrestamoOptional = gestionPrestamRepo.findById(id);

            if (PrestamoOptional.isPresent()) {
                Prestamo Prestamo = PrestamoOptional.get();
                gestionPrestamRepo.delete(Prestamo);
                metodo.eliminarPrestamo("app/src/main/java/com/example/demo/archivos/prestamos.txt", id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
