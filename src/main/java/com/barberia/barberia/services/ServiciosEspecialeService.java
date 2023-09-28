package com.barberia.barberia.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.barberia.barberia.repository.ServiciosEspecialeRepository;
import com.barberia.barberia.services.ServiciosEspecialeService;
import com.barberia.barberia.entities.ServiciosEspeciale;

import java.util.List;
import java.util.Optional;

@Service
public class ServiciosEspecialeService {

    private final ServiciosEspecialeRepository serviciosEspecialeRepository;

    @Autowired
    public ServiciosEspecialeService(ServiciosEspecialeRepository serviciosEspecialeRepository) {
        this.serviciosEspecialeRepository = serviciosEspecialeRepository;
    }

    public List<ServiciosEspeciale> getAllServiciosEspeciales() {
        return serviciosEspecialeRepository.findAll();
    }

    public Optional<ServiciosEspeciale> getServicioEspecialeById(Integer id) {
        return serviciosEspecialeRepository.findById(id);
    }

    public ServiciosEspeciale saveServicioEspeciale(ServiciosEspeciale servicioEspeciale) {
        return serviciosEspecialeRepository.save(servicioEspeciale);
    }

    public void deleteServicioEspeciale(Integer id) {
        serviciosEspecialeRepository.deleteById(id);
    }
}

