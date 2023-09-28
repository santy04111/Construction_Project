package com.barberia.barberia.controllers;

import com.barberia.barberia.services.ServiciosEspecialeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.barberia.barberia.entities.ServiciosEspeciale;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/servicios-especiales")
public class ServiciosEspecialeController {

    private final ServiciosEspecialeService serviciosEspecialeService;

    @Autowired
    public ServiciosEspecialeController(ServiciosEspecialeService serviciosEspecialeService) {
        this.serviciosEspecialeService = serviciosEspecialeService;
    }

    @GetMapping
    public List<ServiciosEspeciale> getAllServiciosEspeciales() {
        return serviciosEspecialeService.getAllServiciosEspeciales();
    }

    @GetMapping("/{id}")
    public Optional<ServiciosEspeciale> getServicioEspecialeById(@PathVariable Integer id) {
        return serviciosEspecialeService.getServicioEspecialeById(id);
    }

    @PostMapping
    public ServiciosEspeciale createServicioEspeciale(@RequestBody ServiciosEspeciale servicioEspeciale) {
        return serviciosEspecialeService.saveServicioEspeciale(servicioEspeciale);
    }

    @DeleteMapping("/{id}")
    public void deleteServicioEspeciale(@PathVariable Integer id) {
        serviciosEspecialeService.deleteServicioEspeciale(id);
    }
}
