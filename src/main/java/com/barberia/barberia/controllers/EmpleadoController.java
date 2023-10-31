package com.barberia.barberia.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.barberia.barberia.services.EmpleadoService;
import com.barberia.barberia.entities.Empleado;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("empleados")
public class EmpleadoController {

    private final EmpleadoService EmpleadoService;

    @Autowired
    public EmpleadoController(EmpleadoService empleadoService) {
        this.EmpleadoService = empleadoService;
    }

    @GetMapping
    public List<Empleado> getAllEmpleados() {
        return EmpleadoService.getAllEmpleados();
    }

    @GetMapping("/{id}")
    public Optional<Empleado> getEmpleadoById(@PathVariable Integer id) {
        return EmpleadoService.getEmpleadoById(id);
    }

    @PostMapping
    public Empleado createEmpleado(@RequestBody Empleado empleado) {
        return EmpleadoService.saveEmpleado(empleado);
    }

    @PutMapping("/{id}")
    public Empleado updateEmpleado(@PathVariable Integer id, @RequestBody Empleado empleado) {
        return EmpleadoService.updateEmpleado(id, empleado);
    }

    @DeleteMapping("/{id}")
    public void deleteEmpleado(@PathVariable Integer id) {
        EmpleadoService.deleteEmpleado(id);
    }
}
