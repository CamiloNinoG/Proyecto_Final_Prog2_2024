package com.sustfinal.app.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.sustfinal.app.domain.Vehiculo;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {
    List<Vehiculo> findByModelo(String modelo);
}
