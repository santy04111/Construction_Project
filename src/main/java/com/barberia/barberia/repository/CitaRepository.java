package com.barberia.barberia.repository;

import com.barberia.barberia.entities.Barbero;
import com.barberia.barberia.entities.Cita;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Integer> {

    List<Cita> findByInicioBetweenAndBarbero1(
            OffsetDateTime inicio,
            OffsetDateTime finalizacion,
            Barbero barbero
    );

    // Puedes agregar consultas personalizadas si es necesario
    @Query("SELECT COUNT(c) FROM Cita c WHERE c.inicio BETWEEN :inicio AND :finalizacion AND c.barbero1 = :barbero")
    int countCitasEnHorario(
            @Param("inicio") OffsetDateTime inicio,
            @Param("finalizacion") OffsetDateTime finalizacion,
            @Param("barbero") Barbero barbero
    );
}

