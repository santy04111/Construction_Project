package com.barberia.barberia.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.barberia.barberia.repository.ServicioRepository;
import com.barberia.barberia.entities.Servicio;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioService {
    private final ServicioRepository servicioRepository;

    @Autowired
    public ServicioService(ServicioRepository servicioRepository) {
        this.servicioRepository = servicioRepository;
    }

    public List<Servicio> getAllServicios() {
        return servicioRepository.findAll();
    }

    public Optional<Servicio> getServicioById(Integer id) {
        return servicioRepository.findById(id);
    }

    public Servicio createServicio(Servicio servicio) {
        return servicioRepository.save(servicio);
    }

    public Servicio updateServicio(Integer id, Servicio servicio) {
        if (servicioRepository.existsById(id)) {
            servicio.setId(id);
            return servicioRepository.save(servicio);
        }
        return null; // Maneja la lógica de manejo de errores aquí
    }

    public void deleteServicio(Integer id) {
        servicioRepository.deleteById(id);
    }
}

