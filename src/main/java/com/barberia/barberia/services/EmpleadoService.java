package com.barberia.barberia.services;

import com.barberia.barberia.entities.Empleado;
import com.barberia.barberia.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import com.barberia.barberia.exceptions.EmpleadoNotFountException;

@Service
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    @Autowired
    public EmpleadoService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    public List<Empleado> getAllEmpleados() {
        return empleadoRepository.findAll();
    }

    public Optional<Empleado> getEmpleadoById(Integer id) {
        return empleadoRepository.findById(id);
    }

    public Empleado saveEmpleado(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    public Empleado updateEmpleado(Integer id, Empleado empleado) {
        // Verificar si el empleado con el ID especificado existe
        if (empleadoRepository.existsById(id)) {
            // Configurar el ID del empleado para asegurar que se actualiza el registro correcto
            empleado.setId(id);
            return empleadoRepository.save(empleado);
        } else {
            // Manejo de error o excepción si el empleado no existe
            throw new EmpleadoNotFountException("Empleado no encontrado con ID: " + id);
        }
    }

    public void deleteEmpleado(Integer id) {
        empleadoRepository.deleteById(id);
    }
}