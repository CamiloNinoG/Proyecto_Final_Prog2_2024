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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Usuario;
import com.example.demo.metodosArchivos.Metodo;
import com.example.demo.repository.GestionUsuarioRepo;

@CrossOrigin(origins = "http://localhost:8085")
@RestController
@RequestMapping("/api")
public class GestionUsuarioController {

    Metodo metodo = new Metodo();

    @Autowired
    GestionUsuarioRepo gestionUsuarioRepository;

    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> getAllUsuarios(@RequestParam(required = false) String name) {
        try {
            List<Usuario> usuarios = new ArrayList<>();

            if (name == null) {
                usuarios.addAll(gestionUsuarioRepository.findAll());
            } else {
                usuarios.addAll(gestionUsuarioRepository.findByName(name));
            }

            return new ResponseEntity<>(usuarios, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable("id") Long id) {
        Optional<Usuario> usuarioData = gestionUsuarioRepository.findById(id);

        if (usuarioData.isPresent()) {
            return new ResponseEntity<>(usuarioData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/usuarios")
    public  ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario _usuario = gestionUsuarioRepository.save(usuario);
            metodo.agergarUsuario("app/src/main/java/com/example/demo/archivos/usuarios.txt", _usuario);          
            return new ResponseEntity<>(_usuario, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

                }
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable("id") Long id, @RequestBody Usuario usuario) {
        Optional<Usuario> usuarioData = gestionUsuarioRepository.findById(id);

        if (usuarioData.isPresent()) {
            Usuario _usuario = usuarioData.get();
            _usuario.setName(usuario.getName());
            _usuario.setLast_name(usuario.getLast_name());
            _usuario.setEmail(usuario.getEmail());
            _usuario.setPassword(usuario.getPassword());
            _usuario.setRol(usuario.getRol());
            gestionUsuarioRepository.save(_usuario);

            metodo.actualizarUsuario("app/src/main/java/com/example/demo/archivos/usuarios.txt", id, _usuario);
            return new ResponseEntity<>(_usuario, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<HttpStatus> deleteUsuarioById(@PathVariable("id") Long id) {
        try {
            Optional<Usuario> usuarioOptional = gestionUsuarioRepository.findById(id);

            if (usuarioOptional.isPresent()) {
                gestionUsuarioRepository.delete(usuarioOptional.get());
                metodo.eliminarUsuario("app/src/main/java/com/example/demo/archivos/usuarios.txt", id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
