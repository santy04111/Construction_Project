package com.barberia.barberia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.barberia.barberia.services.ServicioService;
import com.barberia.barberia.entities.Servicio;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/servicios")
public class ServicioController {
    private final ServicioService servicioService;

    @Autowired
    public ServicioController(ServicioService servicioService) {
        this.servicioService = servicioService;
    }

    @GetMapping
    public List<Servicio> getAllServicios() {
        return servicioService.getAllServicios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servicio> getServicioById(@PathVariable Integer id) {
        Optional<Servicio> servicio = servicioService.getServicioById(id);
        if (servicio.isPresent()) {
            return ResponseEntity.ok(servicio.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public Servicio createServicio(@RequestBody Servicio servicio) {
        return servicioService.createServicio(servicio);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Servicio> updateServicio(@PathVariable Integer id, @RequestBody Servicio servicio) {
        Servicio updatedServicio = servicioService.updateServicio(id, servicio);
        if (updatedServicio != null) {
            return ResponseEntity.ok(updatedServicio);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServicio(@PathVariable Integer id) {
        servicioService.deleteServicio(id);
        return ResponseEntity.noContent().build();
    }
}
