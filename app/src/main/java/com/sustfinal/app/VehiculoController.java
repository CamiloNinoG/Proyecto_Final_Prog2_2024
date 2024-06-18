package com.sustfinal.app;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sustfinal.app.domain.Vehiculo;
import com.sustfinal.app.repository.VehiculoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;


@Controller
public class VehiculoController {

    @Autowired
    VehiculoRepository vehiculoRepository;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    
    @PostMapping("/vehiculos")
    public String createvehiculo(@ModelAttribute Vehiculo vehiculo, RedirectAttributes redirectAttributes) {
        try {
            Vehiculo _vehiculo = vehiculoRepository.save(vehiculo);

            redirectAttributes.addFlashAttribute("message", "Vehiculo guardado exitosamente.");

            return "redirect:/vehiculos";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error al guardar el vehiculo.");
            return "redirect:/vehiculos";
        }
    }

    @GetMapping("/vehiculos")
    public String listarvehiculos(Model model) {
        List<Vehiculo> vehiculos = vehiculoRepository.findAll();
        model.addAttribute("vehiculos", vehiculos);
        return "vehiculos";
    }
    @GetMapping("/agregarVehiculo")
    public String agregarVehiculo(Model model) {
        model.addAttribute("vehiculo", new Vehiculo());
        return "agregarVehiculo";
    }
    
}
