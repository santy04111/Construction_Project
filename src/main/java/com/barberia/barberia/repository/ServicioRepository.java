package com.barberia.barberia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.barberia.barberia.entities.Servicio;
import com.barberia.barberia.entities.Servicio;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Integer> {
    // Puedes agregar métodos de consulta personalizados aquí si es necesario
}