package com.barberia.barberia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.barberia.barberia.entities.ServiciosEspeciale;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface ServiciosEspecialeRepository extends JpaRepository<ServiciosEspeciale, Integer> {

    //Consulta nativa para obtener todos los servicios disponibles
    @Query(value = "SELECT * FROM \"Servicios Especiales\" WHERE \"Disponibilidad\" = true", nativeQuery = true)
    List<ServiciosEspeciale> findServiciosDisponibles();


    //Consulta nativa para obtener todos los servicios en un estado específico
    @Query(value = "SELECT * FROM \"Servicios Especiales\" WHERE \"Estado\" = :estado", nativeQuery = true)
    List<ServiciosEspeciale> findServiciosByEstado(@Param("estado") boolean estado);

    //Consulta nativa para contar la cantidad de servicios disponibles:
    @Query(value = "SELECT COUNT(*) FROM \"Servicios Especiales\" WHERE \"Disponibilidad\" = true", nativeQuery = true)
    int countServiciosDisponibles();
}
