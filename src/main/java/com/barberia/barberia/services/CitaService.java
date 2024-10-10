package com.barberia.barberia.services;

import com.barberia.barberia.entities.Cita;
import com.barberia.barberia.entities.Seguimiento;
import com.barberia.barberia.exceptions.*;
import com.barberia.barberia.repository.BarberoRepository;
import com.barberia.barberia.repository.SeguimientoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.barberia.barberia.repository.CitaRepository;


import java.lang.IllegalStateException;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Transactional
@Service
public class CitaService {

    private final CitaRepository citaRepository;
    private final BarberoRepository barberoRepository;
    private final EmailService emailService;
    private final SeguimientoRepository seguimientoRepository;
    private final AtomicBoolean estaProcesandoCita = new AtomicBoolean(false);

    @Autowired
    public CitaService(CitaRepository citaRepository, BarberoRepository barberoRepository,
                       EmailService emailService, SeguimientoRepository seguimientoRepository) {
        this.citaRepository = citaRepository;
        this.barberoRepository = barberoRepository;
        this.emailService = emailService;
        this.seguimientoRepository = seguimientoRepository;
    }

    public List<Cita> getAllCitas() {
        return citaRepository.findAll();
    }

    public Optional<Cita> getCitaById(Integer id) {
        return citaRepository.findById(id);
    }

    public Cita saveCita(Cita cita) throws CorreoUsuarioNoDisponibleException, SolicitudDuplicadaException {
        if (estaProcesandoCita.compareAndSet(false, true)) {
            try {
                if (barberoRepository.existsById(cita.getBarbero1().getId())) {
                    if (!esFechaHoraPasada(cita.getInicio())) {
                        if (horarioDisponible(cita)) {
                            Cita nuevaCita = citaRepository.save(cita);
                            registrarSeguimiento(nuevaCita, "creada", 1);

                            if (cita.getRecordatorio()) {
                                enviarRecordatorioPorCorreo(nuevaCita);
                            }

                            return nuevaCita;
                        } else {
                            throw new HorarioNoDisponibleException("El horario no está disponible.");
                        }
                    } else {
                        throw new FechaHoraPasadaException("La fecha y hora de la cita están en el pasado.");
                    }
                } else {
                    throw new BarberoNoExisteException("El barbero con id " + cita.getBarbero1().getId() + " no existe.");
                }
            } finally {
                estaProcesandoCita.set(false);
            }
        } else {
            enviarCorreoOperacionConcurrente();
            throw new SolicitudDuplicadaException("No se pueden procesar dos citas al mismo tiempo.");
        }
    }

    public Cita actualizarEstadoCita(Integer idCita, String nuevoEstado) {
        Cita cita = citaRepository.findById(idCita)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada"));

        String estadoActual = cita.getEstado();

        // Definir las transiciones permitidas
        Map<String, List<String>> transicionesPermitidas = new HashMap<>();
        transicionesPermitidas.put("creada", Arrays.asList("confirmada", "cancelada"));
        transicionesPermitidas.put("confirmada", Arrays.asList("completada", "cancelada"));
        transicionesPermitidas.put("cancelada", Collections.emptyList()); // No se puede cambiar después de ser cancelada
        transicionesPermitidas.put("completada", Collections.emptyList()); // No se puede cambiar después de ser completada

        // Verificar si la transición es válida
        if (!transicionesPermitidas.containsKey(estadoActual) ||
                !transicionesPermitidas.get(estadoActual).contains(nuevoEstado)) {
            throw new IllegalStateException("No se puede cambiar el estado de " + estadoActual + " a " + nuevoEstado);
        }

        // Actualizar el estado de la cita si pasa la validación
        cita.setEstado(nuevoEstado);
        citaRepository.save(cita);

        // Registrar el cambio de estado en la tabla seguimiento
        registrarSeguimiento(cita, nuevoEstado, 1);

        // Enviar notificación por correo
        enviarNotificacionCambioEstado(cita);

        return cita;
    }

//    // Método separado para validar el cambio de estado
//    private void validarCambioDeEstado(String estadoActual, String nuevoEstado) throws EstadoInvalidoException {
//        System.out.println("Estado actual: " + estadoActual + ", Nuevo estado: " + nuevoEstado); // Depuración
//
//        // No permitir cambios de estado desde cancelada o finalizada
//        if (estadoActual.equals("cancelada") || estadoActual.equals("finalizada")) {
//            throw new EstadoInvalidoException("No se puede cambiar el estado de una cita cancelada o finalizada.");
//        }
//
//        // Validaciones adicionales según el estado actual
//        if (estadoActual.equals("creada") && (!nuevoEstado.equals("confirmada") && !nuevoEstado.equals("cancelada"))) {
//            throw new EstadoInvalidoException("Desde 'creada', solo se puede cambiar a 'confirmada' o 'cancelada'.");
//        }
//        if (estadoActual.equals("confirmada") && (!nuevoEstado.equals("finalizada") && !nuevoEstado.equals("cancelada"))) {
//            throw new EstadoInvalidoException("Desde 'confirmada', solo se puede cambiar a 'finalizada' o 'cancelada'.");
//        }
//    }




    // Registrar el seguimiento del estado
    private void registrarSeguimiento(Cita cita, String nuevoEstado, Integer vigente) {
        // Primero, marcar el seguimiento vigente actual como no vigente (vigente = 0)
        List<Seguimiento> seguimientosVigentes = seguimientoRepository.findByCitaAndVigente(cita, 1);
        for (Seguimiento seguimiento : seguimientosVigentes) {
            seguimiento.setVigente(0);
            seguimientoRepository.save(seguimiento);
        }

        // Luego, registrar el nuevo seguimiento con el nuevo estado y vigente = 1
        Seguimiento nuevoSeguimiento = Seguimiento.builder()
                .cita(cita)
                .codigoEstado(nuevoEstado)
                .vigente(1)
                .fechaSeguimiento(OffsetDateTime.now())
                .build();

        seguimientoRepository.save(nuevoSeguimiento);
    }

    // Enviar notificación por correo cuando el estado de la cita cambia
    private void enviarNotificacionCambioEstado(Cita cita) {
        String to = cita.getEmail();
        String subject = "Actualización del estado de su cita";
        String body = "El estado de su cita ha sido actualizado a: " + cita.getEstado() + "\n" +
                "Detalles de la cita:\n" +
                "Fecha y hora de inicio: " + cita.getInicio() + "\n" +
                "Barbero: " + cita.getBarbero1().getNombre();

        emailService.sendEmailwithAttachment(to, subject, body);
    }

    // Método para enviar un correo en caso de operaciones concurrentes
    private void enviarCorreoOperacionConcurrente() {
        String to = "gonzaleztrianakevin@hotmail.com";
        String subject = "Operación Concurrente Detectada";
        String body = "Se ha intentado registrar una cita mientras otra estaba en proceso. Intente nuevamente.";
        emailService.sendEmailwithAttachment(to, subject, body);
    }

    private void enviarRecordatorioPorCorreo(Cita cita) throws CorreoUsuarioNoDisponibleException {
        String to = cita.getEmail();
        if (to == null || to.isEmpty()) {
            throw new CorreoUsuarioNoDisponibleException("El correo del usuario no está disponible para enviar el recordatorio.");
        }

        String subject = "Recordatorio de cita";
        String body = "Detalles de la cita:\n" +
                "Fecha y hora: " + cita.getInicio() + "\n" +
                "Barbero: " + cita.getBarbero1().getNombre() + "\n" +
                "Otro detalle necesario...";

        emailService.sendEmailwithAttachment(to, subject, body);
    }

    private boolean horarioDisponible(Cita nuevaCita) {
        int count = citaRepository.countCitasEnHorario(
                nuevaCita.getInicio().minusMinutes(30),
                nuevaCita.getFinalizacion().plusMinutes(30),
                nuevaCita.getBarbero1()
        );
        return count == 0;
    }

    private boolean esFechaHoraPasada(OffsetDateTime fechaHora) {
        return fechaHora.isBefore(OffsetDateTime.now());
    }

    public void deleteCita(Integer id) {
        citaRepository.deleteById(id);
    }
}



