package com.example.demo;

import com.example.demo.domain.Libro;
import com.example.demo.metodosArchivos.Metodo;
import com.example.demo.repository.GestionLibroRepo;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class LibroController {

    Metodo metodo = new Metodo();

    @Autowired
    private GestionLibroRepo libroRepository;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/libros1")
    public String listarLibros1(Model model) {
        List<Libro> libros = libroRepository.findAll();
        model.addAttribute("libros", libros);
        return "libros1";
    }

    @PostMapping("/buscarLibro")
    public String buscarLibroPorIsbn(@RequestParam("isbnInput") String isbn, Model model) {
        List<Libro> librosEncontrados = libroRepository.findByIsbn(isbn);
        model.addAttribute("libros", librosEncontrados);
        return "libros";
    }

    @GetMapping("/libros")
    public String listarLibros(Model model) {
        List<Libro> libros = libroRepository.findAll();
        model.addAttribute("libros", libros);
        return "libros";
    }

    @GetMapping("/opcionesAdmin")
    public String opcionesAdmin() {
        return "opcionesAdmin";
    }

    @GetMapping("/agregarLibro")
    public String agregarLibro(Model model) {
        model.addAttribute("libro", new Libro());
        return "agregarLibro";
    }

    @PostMapping("/libros")
    public String createLibro(@ModelAttribute Libro libro, RedirectAttributes redirectAttributes) {
        try {
            Libro _libro = libroRepository.save(libro);

            redirectAttributes.addFlashAttribute("message", "Libro guardado exitosamente.");

            metodo.agregarLibro("app/src/main/java/com/example/demo/archivos/libros.txt", _libro);
            return "redirect:/libros";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error al guardar el libro.");
            return "redirect:/libros";
        }
    }

    @PostMapping("/eliminarLibro/{id}")
    public String eliminarLibro(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            Optional<Libro> libroOptional = libroRepository.findById(id);

            if (libroOptional.isPresent()) {
                Libro libro = libroOptional.get();
                libroRepository.delete(libro);

                metodo.eliminarLibro("app/src/main/java/com/example/demo/archivos/libros.txt", id);
                redirectAttributes.addFlashAttribute("message", "Libro eliminado exitosamente.");
            } else {
                redirectAttributes.addFlashAttribute("message", "No se encontró el libro a eliminar.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error al eliminar el libro.");
        }
        return "redirect:/libros";
    }

    // editar libro
    @GetMapping("/editarLibro/{id}")
    public String formularioEditar(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Libro> libroOptional = libroRepository.findById(id);
        if (libroOptional.isPresent()) {
            model.addAttribute("libro", libroOptional.get());
            return "editarLibro";
        } else {
            redirectAttributes.addFlashAttribute("message", "No se encontró el libro a editar.");
            return "redirect:/libros";
        }
    }

    @PostMapping("/editarLibro")
    public String editarLibro(@ModelAttribute Libro libro, RedirectAttributes redirectAttributes) {
        try {
            libroRepository.save(libro);
            metodo.actualizarLibro("app/src/main/java/com/example/demo/archivos/libros.txt", libro.getId(), libro);
            redirectAttributes.addFlashAttribute("message", "Libro actualizado exitosamente.");
            return "redirect:/libros";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error al actualizar el libro.");
            return "redirect:/libros";
        }
    }
        // editar libro

}
