package com.barberia.barberia.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.barberia.barberia.entities.Cita;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Integer> {
    // Puedes agregar consultas específicas si es necesario
}
