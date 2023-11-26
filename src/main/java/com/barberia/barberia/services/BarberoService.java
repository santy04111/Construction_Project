package com.barberia.barberia.services;

import java.util.List;
import java.util.Optional;

import com.barberia.barberia.entities.Barbero;
import com.barberia.barberia.repository.BarberoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BarberoService {

    private final BarberoRepository barberoRepository;

    @Autowired
    public BarberoService(BarberoRepository barberoRepository) {
        this.barberoRepository = barberoRepository;
    }

    public List<Barbero> getAllBarberos() {
        return barberoRepository.findAll();
    }

    public Optional<Barbero> getBarberoById(Integer id) {
        return barberoRepository.findById(id);
    }

    public Barbero saveBarbero(Barbero barbero) {
        return barberoRepository.save(barbero);
    }

    public void deleteBarbero(Integer id) {
        barberoRepository.deleteById(id);
    }
}

