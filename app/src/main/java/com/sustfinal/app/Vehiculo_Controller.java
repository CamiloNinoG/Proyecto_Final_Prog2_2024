package com.sustfinal.app;

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

import com.sustfinal.app.domain.Vehiculo;
import com.sustfinal.app.repository.VehiculoRepository;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@CrossOrigin(origins = "http://localhost:8086")
@RestController
@RequestMapping("/api")
public class Vehiculo_Controller {

    @Autowired
    VehiculoRepository vehiculoRepository;

    @GetMapping("/vehiculos")
    public ResponseEntity<List<Vehiculo>> getAllVehiculos(@RequestParam(required = false) String modelo) {
        try {
            List<Vehiculo> vehiculos = new ArrayList<>();

            if (modelo == null) {
                vehiculos.addAll(vehiculoRepository.findAll());
            } else {
                vehiculos.addAll(vehiculoRepository.findByModelo(modelo));
            }

            return new ResponseEntity<>(vehiculos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/vehiculos/{id}")
    public ResponseEntity<Vehiculo> getVehiculoById(@PathVariable("id") Long id) {
        Optional<Vehiculo> vehiculoData = vehiculoRepository.findById(id);

        if (vehiculoData.isPresent()) {
            return new ResponseEntity<>(vehiculoData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

   @PostMapping("/vehiculos")
    public ResponseEntity<Vehiculo> createVehiculo(@RequestBody Vehiculo vehiculo) {
        try {
            Vehiculo _vehiculo = vehiculoRepository.save(new Vehiculo(vehiculo.getId(), vehiculo.getMarca(), vehiculo.getModelo(), vehiculo.getAño(), vehiculo.getColor(), vehiculo.getPrecio(), vehiculo.getTipo(), vehiculo.getConsecionario()));          
            return new ResponseEntity<>(_vehiculo, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);


        }
    }
    

    @PutMapping("/vehiculos/{id}")
    public ResponseEntity<Vehiculo> updateVehiculo(@PathVariable("id") Long id, @RequestBody Vehiculo vehiculo) {
        Optional<Vehiculo> vehiculoData = vehiculoRepository.findById(id);

        if (vehiculoData.isPresent()) {
            Vehiculo _vehiculo = vehiculoData.get();
            _vehiculo.setMarca(vehiculo.getMarca());
            _vehiculo.setModelo(vehiculo.getModelo());
            _vehiculo.setAño(vehiculo.getAño());
            _vehiculo.setColor(vehiculo.getColor());
            _vehiculo.setPrecio(vehiculo.getPrecio());
            _vehiculo.setTipo(vehiculo.getTipo());
            _vehiculo.setConsecionario(vehiculo.getConsecionario());
            vehiculoRepository.save(_vehiculo);
            return new ResponseEntity<>(_vehiculo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/vehiculos/{id}")
    public ResponseEntity<HttpStatus> deleteVehiculoById(@PathVariable("id") Long id) {
        try {
            Optional<Vehiculo> vehiculoOptional = vehiculoRepository.findById(id);
            
            if (vehiculoOptional.isPresent()) {
                Vehiculo vehiculo = vehiculoOptional.get();
                vehiculoRepository.delete(vehiculo);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

