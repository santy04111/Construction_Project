package com.barberia.barberia.services;

import com.barberia.barberia.entities.Barbero;
import com.barberia.barberia.entities.Cita;
import com.barberia.barberia.exceptions.BarberoNoExisteException;
import com.barberia.barberia.repository.BarberoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.barberia.barberia.repository.CitaRepository;
import com.barberia.barberia.exceptions.HorarioNoDisponibleException;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class CitaService {


    public List<Cita> getAllCitas() {
        return citaRepository.findAll();
    }

    public Optional<Cita> getCitaById(Integer id) {
        return citaRepository.findById(id);
    }

    private final CitaRepository citaRepository;
    private final BarberoRepository barberoRepository;

    @Autowired
    public CitaService(CitaRepository citaRepository, BarberoRepository barberoRepository) {
        this.citaRepository = citaRepository;
        this.barberoRepository = barberoRepository;
    }

    public Cita saveCita(Cita cita) {
        Barbero barbero = cita.getBarbero1();

        // Verificar si el barbero existe
        Optional<Barbero> barberoOptional = barberoRepository.findById(barbero.getId());
        if (barberoOptional.isEmpty()) {
            throw new BarberoNoExisteException("El barbero con ID " + barbero.getId() + " no existe.");
        }

        LocalDateTime inicio = cita.getInicio().toLocalDateTime();
        LocalDateTime finalizacion = cita.getFinalizacion().toLocalDateTime();

        // Verificar si el horario está ocupado
        if (horarioEstaOcupado(inicio, finalizacion, barbero)) {
            throw new HorarioNoDisponibleException("El horario seleccionado no está disponible para el barbero.");
        }

        return citaRepository.save(cita);
    }

    private boolean horarioEstaOcupado(LocalDateTime inicio, LocalDateTime finalizacion, Barbero barbero) {
        List<Cita> citasEnHorario = citaRepository.findByBarberoAndInicioBetweenAndFinalizacionBetween(
                barbero, inicio, finalizacion, inicio, finalizacion);

        return !citasEnHorario.isEmpty();
    }

    // ...


    public void deleteCita(Integer id) {
        citaRepository.deleteById(id);
    }

    public Cita crearNuevaCita(Cita reservaRequest) {
        return saveCita(reservaRequest);
    }





}


