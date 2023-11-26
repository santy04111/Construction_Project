package com.barberia.barberia.controllers;

import java.util.List;
import java.util.Optional;

import com.barberia.barberia.entities.Barbero;
import com.barberia.barberia.services.BarberoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/barberos")
public class BarberoController {

    private final BarberoService barberoService;

    @Autowired
    public BarberoController(BarberoService barberoService) {
        this.barberoService = barberoService;
    }

    @GetMapping
    public ResponseEntity<List<Barbero>> getAllBarberos() {
        List<Barbero> barberos = barberoService.getAllBarberos();
        return new ResponseEntity<>(barberos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Barbero> getBarberoById(@PathVariable Integer id) {
        Optional<Barbero> barbero = barberoService.getBarberoById(id);
        return barbero.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Barbero> saveBarbero(@RequestBody Barbero barbero) {
        Barbero savedBarbero = barberoService.saveBarbero(barbero);
        return new ResponseEntity<>(savedBarbero, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBarbero(@PathVariable Integer id) {
        barberoService.deleteBarbero(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

