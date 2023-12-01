package com.barberia.barberia.services;

import com.barberia.barberia.entities.Barbero;
import com.barberia.barberia.entities.Cita;
import com.barberia.barberia.exceptions.*;
import com.barberia.barberia.repository.BarberoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import com.barberia.barberia.repository.CitaRepository;


import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Transactional
@Service
public class CitaService {
    private final CitaRepository citaRepository;
    private final BarberoRepository barberoRepository;
    private final EmailService emailService;

    private final AtomicBoolean estaProcesandoCita = new AtomicBoolean(false);


    @Autowired
    public CitaService(CitaRepository citaRepository, BarberoRepository barberoRepository, EmailService emailService) {
        this.citaRepository = citaRepository;
        this.barberoRepository = barberoRepository;
        this.emailService = emailService;
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


    private void enviarCorreoOperacionConcurrente() {
        String to = "gonzaleztrianakevin@hotmail.com";
        String subject = "Operación Concurrente Detectada";
        String body = "Se ha intentado registrar una cita mientras otra estaba en proceso. Intente nuevamente.";

        emailService.sendEmailwithAttachment(to, subject, body);
    }



    private void enviarRecordatorioPorCorreo(Cita cita) throws CorreoUsuarioNoDisponibleException {
        // Obtener el correo del usuario registrado para la cita
        String to = cita.getEmail(); // Asegúrate de tener el método getCorreoUsuario() en tu entidad Cita
        if (to == null || to.isEmpty()) {
            // Si el correo del usuario no está disponible, puedes manejarlo de alguna manera (por ejemplo, lanzar una excepción)
            throw new CorreoUsuarioNoDisponibleException("El correo del usuario no está disponible para enviar el recordatorio.");
        }

        // Preparar el cuerpo del correo con los detalles de la cita
        String subject = "Recordatorio de cita";
        String body = "Detalles de la cita:\n" +
                "Fecha y hora: " + cita.getInicio() + "\n" +
                "Barbero: " + cita.getBarbero1().getNombre() + "\n" +
                "Otro detalle necesario..."; // Agregar otros detalles que desees enviar en el correo

        // Enviar el correo
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



