package com.barberia.barberia.services;

import com.barberia.barberia.entities.Barbero;
import com.barberia.barberia.entities.Cita;
import com.barberia.barberia.exceptions.BarberoNoExisteException;
import com.barberia.barberia.exceptions.FechaHoraPasadaException;
import com.barberia.barberia.repository.BarberoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.barberia.barberia.repository.CitaRepository;
import com.barberia.barberia.exceptions.HorarioNoDisponibleException;


import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class CitaService {
    private final CitaRepository citaRepository;
    private final BarberoRepository barberoRepository;

    @Autowired
    public CitaService(CitaRepository citaRepository, BarberoRepository barberoRepository) {
        this.citaRepository = citaRepository;
        this.barberoRepository = barberoRepository;
    }

    public List<Cita> getAllCitas() {
        return citaRepository.findAll();
    }

    public Optional<Cita> getCitaById(Integer id) {
        return citaRepository.findById(id);
    }

    public Cita saveCita(Cita cita) {
        if (barberoRepository.existsById(cita.getBarbero1().getId())) {
            if (!esFechaHoraPasada(cita.getInicio())) {
                if (horarioDisponible(cita)) {
                    return citaRepository.save(cita);
                } else {
                    throw new HorarioNoDisponibleException("El horario no está disponible.");
                }
            } else {
                throw new FechaHoraPasadaException("La fecha y hora de la cita están en el pasado.");
            }
        } else {
            throw new BarberoNoExisteException("El barbero con id " + cita.getBarbero1().getId() + " no existe.");
        }
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


