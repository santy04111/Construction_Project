package com.barberia.barberia.repository;
import com.barberia.barberia.entities.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {

    // Consulta nativa para obtener empleados por nombre y apellido
    @Query(value = "SELECT * FROM empleados WHERE nombre = :nombre AND apellido = :apellido", nativeQuery = true)
    List<Empleado> findEmpleadosByNombreAndApellido(@Param("nombre") String nombre, @Param("apellido") String apellido);

    // Otra consulta nativa personalizada
    @Query(value = "SELECT * FROM empleados WHERE puesto = :puesto", nativeQuery = true)
    List<Empleado> findEmpleadosByPuesto(@Param("puesto") String puesto);

}
