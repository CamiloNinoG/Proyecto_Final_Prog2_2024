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

import com.example.demo.domain.Usuario;
import com.example.demo.metodosArchivos.Metodo;
import com.example.demo.repository.GestionUsuarioRepo;

@Controller
public class UsuariosController {

    @Autowired
    Metodo metodo = new Metodo();

    @Autowired
    private GestionUsuarioRepo usuarioRepository;

    @GetMapping("/usuarios1")
    public String listarUsuarios1(Model model) {
        List<Usuario> usuarios = usuarioRepository.findAll();
        model.addAttribute("usuarios", usuarios);
        return "usuarios1";
    }

    @GetMapping("/opcionUsuario")
    public String opcionUsuario() {
            return "/opcionUsuario";
        }

    @GetMapping("/usuarios")
    public String listarUsuarios(Model model) {
        List<Usuario> usuarios = usuarioRepository.findAll();
        model.addAttribute("usuarios", usuarios);
        return "usuarios";
    }

    @PostMapping("/buscarUsuario")
    public String buscarUsuarioPorNombre(@RequestParam("isbnInput") String name, Model model) {
        List<Usuario> usuarioEncontrado = usuarioRepository.findByName(name);
        model.addAttribute("usuarios", usuarioEncontrado);
        return "usuarios";
    }

    @GetMapping("/añadirUsuario")
    public String añadirUsuario() {
        return "añadirUsuario";
    }

    @PostMapping("/usuarios")
    public String createUsuario(@ModelAttribute Usuario usuario, RedirectAttributes redirectAttributes) {
        try {
            Usuario _usuario = usuarioRepository.save(usuario);
            redirectAttributes.addFlashAttribute("message", "Usuario guardado exitosamente.");
            metodo.agergarUsuario("app/src/main/java/com/example/demo/archivos/usuarios.txt", _usuario);
            return "redirect:/usuarios";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error al guardar el usuario.");
            return "redirect:/usuarios";
        }
    }

    @PostMapping("/eliminarUsuario/{id}")
    public String deleteUsuario(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

            if (usuarioOptional.isPresent()) {
                Usuario usuario = usuarioOptional.get();
                usuarioRepository.delete(usuario);

                metodo.eliminarUsuario("app/src/main/java/com/example/demo/archivos/usuarios.txt", id);
                redirectAttributes.addFlashAttribute("message", "Usuario eliminado exitosamente.");
            } else {
                redirectAttributes.addFlashAttribute("message", "No se encontró el usuario a eliminar.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error al eliminar el usuario.");
        }
        return "redirect:/usuarios";
    }

    // editar usuario
    @GetMapping("/editarUsuario/{id}")
    public String formularioEditar(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isPresent()) {
            model.addAttribute("usuario", usuarioOptional.get());
            return "editarUsuario";
        } else {
            redirectAttributes.addFlashAttribute("message", "No se encontró el usuario a editar.");
            return "redirect:/usuarios";
        }
    }

    @PostMapping("/editarUsuario")
    public String editarusuario(@ModelAttribute Usuario usuario, RedirectAttributes redirectAttributes) {
        try {
            usuarioRepository.save(usuario);
            metodo.actualizarUsuario("app/src/main/java/com/example/demo/archivos/usuarios.txt", usuario.getId(),
                    usuario);
            redirectAttributes.addFlashAttribute("message", "usuario actualizado exitosamente.");
            return "redirect:/usuarios";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error al actualizar el usuario.");
            return "redirect:/usuarios";
        }
    }
    // editar usuario

}
