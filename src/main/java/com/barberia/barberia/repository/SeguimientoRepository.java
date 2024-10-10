package com.barberia.barberia.repository;

import com.barberia.barberia.entities.Cita;
import com.barberia.barberia.entities.Seguimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SeguimientoRepository extends JpaRepository<Seguimiento, UUID> {
    List<Seguimiento> findByCitaAndVigente(Cita cita, Integer vigente);
}
