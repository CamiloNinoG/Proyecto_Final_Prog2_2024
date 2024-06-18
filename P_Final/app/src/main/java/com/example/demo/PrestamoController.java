package com.example.demo;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.domain.Prestamo;
import com.example.demo.metodosArchivos.Metodo;
import com.example.demo.repository.GestionPrestamoRepo;

@Controller
public class PrestamoController {
    
    Metodo metodo = new Metodo();

    @Autowired
    private GestionPrestamoRepo prestamoRepository;

    @GetMapping("/prestamos1")
    public String listarprestamos1(Model model) {
        List<Prestamo> prestamos = prestamoRepository.findAll();
        model.addAttribute("prestamos", prestamos);
        return "prestamos1";
    }

    @GetMapping("/prestamoUsuario")
    public String prestamoUsuario(Model model) {
        List<Prestamo> prestamos = prestamoRepository.findAll();
        model.addAttribute("prestamos", prestamos);
        return "prestamoUsuario";
    }

    @PostMapping("/buscarPrestamo")
    public String buscarprestamoPorIsbn(@RequestParam("isbnInput") String isbn, Model model) {
        List<Prestamo> prestamosEncontrados = prestamoRepository.findByIsbn(isbn);
        model.addAttribute("prestamos", prestamosEncontrados);
        return "prestamos1";
    }

    @GetMapping("/prestamos")
    public String listarprestamos(Model model) {
        List<Prestamo> prestamos = prestamoRepository.findAll();
        model.addAttribute("prestamos", prestamos);
        return "prestamos";
    }

    @GetMapping("/agregarPrestamo")
    public String agregarPrestamo1(Model model) {
        model.addAttribute("prestamo", new Prestamo());
        return "agregarPrestamo";
    }

    @PostMapping("/prestamos")
    public String createprestamo(@ModelAttribute Prestamo prestamo, RedirectAttributes redirectAttributes) {
        try {
            Prestamo _prestamo = prestamoRepository.save(prestamo);

            redirectAttributes.addFlashAttribute("message", "prestamo guardado exitosamente.");

            metodo.agergarPrestamo("app/src/main/java/com/example/demo/archivos/prestamos.txt", _prestamo);
            return "redirect:/prestamos";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error al guardar el prestamo.");
            return "redirect:/prestamos";
        }
    }

    @PostMapping("/eliminarPrestamo/{id}")
    public String eliminarprestamo(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            Optional<Prestamo> prestamoOptional = prestamoRepository.findById(id);

            if (prestamoOptional.isPresent()) {
                Prestamo prestamo = prestamoOptional.get();
                prestamoRepository.delete(prestamo);

                metodo.eliminarPrestamo("app/src/main/java/com/example/demo/archivos/prestamos.txt", id);
                redirectAttributes.addFlashAttribute("message", "prestamo eliminado exitosamente.");
            } else {
                redirectAttributes.addFlashAttribute("message", "No se encontró el prestamo a eliminar.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error al eliminar el prestamo.");
        }
        return "redirect:/prestamos";
    }

    // editar prestamo
    @GetMapping("/editarPrestamo/{id}")
    public String formularioEditar(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Prestamo> prestamoOptional = prestamoRepository.findById(id);
        if (prestamoOptional.isPresent()) {
            model.addAttribute("prestamo", prestamoOptional.get());
            return "editarPrestamo";
        } else {
            redirectAttributes.addFlashAttribute("message", "No se encontró el prestamo a editar.");
            return "redirect:/prestamos";
        }
    }

    @PostMapping("/editarPrestamo")
    public String editarPrestamo(@ModelAttribute Prestamo prestamo, RedirectAttributes redirectAttributes) {
        try {
            prestamoRepository.save(prestamo);
            metodo.actualizarPrestamo("app/src/main/java/com/example/demo/archivos/prestamos.txt", prestamo.getId(), prestamo);
            redirectAttributes.addFlashAttribute("message", "prestamo actualizado exitosamente.");
            return "redirect:/prestamos";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error al actualizar el prestamo.");
            return "redirect:/prestamos";
        }
    }
        // editar prestamo

}
