package com.barberia.barberia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.barberia.barberia.services.CitaService;
import com.barberia.barberia.entities.Cita;

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
    public Optional<Cita> getCitaById(@PathVariable Integer id) {
        return citaService.getCitaById(id);
    }

    @PostMapping
    public Cita saveCita(@RequestBody Cita cita) {
        return citaService.saveCita(cita);
    }

    @DeleteMapping("/{id}")
    public void deleteCita(@PathVariable Integer id) {
        citaService.deleteCita(id);
    }
}