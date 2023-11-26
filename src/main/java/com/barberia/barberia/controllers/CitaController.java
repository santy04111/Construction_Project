package com.barberia.barberia.controllers;

import com.barberia.barberia.entities.Cita;
import com.barberia.barberia.exceptions.HorarioNoDisponibleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.barberia.barberia.services.CitaService;

import java.util.List;

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
        return citaService.getCitaById(id)
                .map(cita -> new ResponseEntity<>(cita, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/nueva")
    public ResponseEntity<?> crearNuevaCita(@RequestBody Cita cita) {
        try {
            Cita nuevaCita = citaService.saveCita(cita);
            return new ResponseEntity<>(nuevaCita, HttpStatus.CREATED);
        } catch (HorarioNoDisponibleException ex) {
            return new ResponseEntity<>("El horario seleccionado no está disponible.", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCita(@PathVariable Integer id) {
        citaService.deleteCita(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}