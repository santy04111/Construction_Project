package com.barberia.barberia.services;

import com.barberia.barberia.entities.Cita;
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

    public Cita crearNuevaCita(Cita reservaRequest) {
        return saveCita(reservaRequest);
    }





}


