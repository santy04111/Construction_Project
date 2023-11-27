package com.barberia.barberia.controllers;

import com.barberia.barberia.entities.Barbero;
import com.barberia.barberia.entities.Cita;
import com.barberia.barberia.exceptions.BarberoNoExisteException;
import com.barberia.barberia.exceptions.FechaHoraPasadaException;
import com.barberia.barberia.exceptions.HorarioNoDisponibleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.barberia.barberia.services.CitaService;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/citas")
public class CitaController {
    private final CitaService citaService;

    @Autowired
    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    @GetMapping
    public List<Cita> getAllCitas() {
        return citaService.getAllCitas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cita> getCitaById(@PathVariable Integer id) {
        Optional<Cita> cita = citaService.getCitaById(id);
        return cita.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/crear")
    public ResponseEntity<?> createCita(@RequestBody Cita cita) {
        try {
            Cita nuevaCita = citaService.saveCita(cita);
            return ResponseEntity.ok(nuevaCita);
        } catch (HorarioNoDisponibleException | BarberoNoExisteException | FechaHoraPasadaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/id")
    public ResponseEntity<Cita> updateCita(@PathVariable Integer id, @RequestBody Cita updatedCita) {
        Optional<Cita> existingCita = citaService.getCitaById(id);
        if (existingCita.isPresent()) {
            updatedCita.setId(id);
            return ResponseEntity.ok(citaService.saveCita(updatedCita));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCita(@PathVariable Integer id) {
        Optional<Cita> existingCita = citaService.getCitaById(id);
        if (existingCita.isPresent()) {
            citaService.deleteCita(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}