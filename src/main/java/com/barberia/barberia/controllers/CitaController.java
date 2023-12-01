package com.barberia.barberia.controllers;

import com.barberia.barberia.entities.Barbero;
import com.barberia.barberia.entities.Cita;
import com.barberia.barberia.exceptions.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    @Operation(summary = "Obtener todas las citas", description = "Obtiene una lista de todas las citas.")
    @GetMapping
    public List<Cita> getAllCitas() {
        return citaService.getAllCitas();
    }

    @Operation(summary = "Obtener cita por ID", description = "Obtiene una cita por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cita encontrada"),
            @ApiResponse(responseCode = "404", description = "Cita no encontrada")
    })
    @GetMapping("/obtenercitas/{id}")
    public ResponseEntity<Cita> getCitaById(
            @Parameter(description = "ID de la cita", required = true) @PathVariable Integer id) {
        Optional<Cita> cita = citaService.getCitaById(id);
        return cita.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear nueva cita", description = "Crea una nueva cita.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cita creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Error en la solicitud"),
            @ApiResponse(responseCode = "409", description = "Conflict: solicitud duplicada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/crearcita")
    public ResponseEntity<?> createCita(
            @Parameter(description = "Datos de la nueva cita", required = true) @RequestBody Cita cita) {
        try {
            Cita nuevaCita = citaService.saveCita(cita);
            return ResponseEntity.ok(nuevaCita);
        } catch (HorarioNoDisponibleException | BarberoNoExisteException | FechaHoraPasadaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (SolicitudDuplicadaException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (CorreoUsuarioNoDisponibleException e) {
            return ResponseEntity.badRequest().body("No se puede enviar el recordatorio. Correo del usuario no disponible.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error inesperado al procesar la cita.");
        }
    }

    @Operation(summary = "Eliminar cita por ID", description = "Elimina una cita por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cita eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cita no encontrada")
    })
    @DeleteMapping("/deletecita/{id}")
    public ResponseEntity<Void> deleteCita(
            @Parameter(description = "ID de la cita a eliminar", required = true) @PathVariable Integer id) {
        Optional<Cita> existingCita = citaService.getCitaById(id);
        if (existingCita.isPresent()) {
            citaService.deleteCita(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
