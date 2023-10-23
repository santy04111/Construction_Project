package com.barberia.barberia.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.barberia.barberia.entities.Cita;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Integer> {

        @Query("SELECT c FROM Cita c WHERE c.barbero = :barbero " +
                "AND ((:inicio BETWEEN c.inicio AND c.finalizacion) OR " +
                "(:finalizacion BETWEEN c.inicio AND c.finalizacion) OR " +
                "(:inicio <= c.inicio AND :finalizacion >= c.finalizacion))")
        List<Cita> findCitasSolapadas(@Param("inicio") LocalDateTime inicio,
                                      @Param("finalizacion") LocalDateTime finalizacion,
                                      @Param("barbero") String barbero);

}
