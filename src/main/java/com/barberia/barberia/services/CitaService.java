package com.barberia.barberia.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.barberia.barberia.repository.CitaRepository;
import com.barberia.barberia.exceptions.HorarioNoDisponibleException;
import com.barberia.barberia.entities.Cita;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CitaService {

    private final CitaRepository citaRepository;

    @Autowired
    public CitaService(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    public List<Cita> getAllCitas() {
        return citaRepository.findAll();
    }

    public Optional<Cita> getCitaById(Integer id) {
        return citaRepository.findById(id);
    }

    public Cita saveCita(Cita cita) {
        return citaRepository.save(cita);
    }

    public void deleteCita(Integer id) {
        citaRepository.deleteById(id);
    }

    private HorarioNoDisponibleException horarioNoDisponibleException;

    public Cita crearNuevaReserva(Cita reservaRequest) {
        // Validar la disponibilidad del horario
        if (!esHorarioDisponible(Cita.getInicio(), reservaRequest.getFinalizacion(), reservaRequest.getBarbero())) {
            throw new HorarioNoDisponibleException("El horario seleccionado no está disponible.");
        }

        // Crear una nueva cita
        Cita nuevaCita = new Cita();
        nuevaCita.setInicio(reservaRequest.getInicio());
        nuevaCita.setFinalizacion(reservaRequest.getFinalizacion());
        nuevaCita.setBarbero(reservaRequest.getBarbero());
        nuevaCita.setCliente(reservaRequest.getCliente());
        nuevaCita.setNotasOComentarios(reservaRequest.getNotasOComentarios());
        nuevaCita.setRecordatorio(false); // Por defecto, el recordatorio está desactivado

        // Guardar la cita en la base de datos
        return citaRepository.save(nuevaCita);
    }

    private boolean esHorarioDisponible(LocalDateTime inicio, LocalDateTime finalizacion, String barbero) {
        // Consulta la base de datos para verificar si hay solapamientos con otras citas del mismo barbero.
        List<Cita> citasSolapadas = citaRepository.findCitasSolapadas(inicio, finalizacion, barbero);

        // Si no hay citas solapadas, el horario está disponible.
        return citasSolapadas.isEmpty();
    }
}


