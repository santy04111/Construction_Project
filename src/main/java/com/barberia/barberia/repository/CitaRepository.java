package com.barberia.barberia.repository;

import com.barberia.barberia.entities.Cita;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Integer> {

//        @Query("SELECT c FROM Cita c WHERE c.Barbero = :Barbero " +
//                "AND ((:Inicio BETWEEN c.Inicio AND c.Finalizacion) OR " +
//                "(:Finalizacion BETWEEN c.Inicio AND c.Finalizacion) OR " +
//                "(:Inicio <= c.Inicio AND :Finalizacion >= c.Finalizacion))")
//        List<Cita> findCitasSolapadas(@Param("Inicio") LocalDateTime Inicio,
//                                      @Param("Finalizacion") LocalDateTime Finalizacion,
//                                      @Param("Barbero") String Barbero);

}
