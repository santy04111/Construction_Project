package com.barberia.barberia;

import com.barberia.barberia.controllers.CitaController;
import com.barberia.barberia.entities.Barbero;
import com.barberia.barberia.entities.Cita;
import com.barberia.barberia.exceptions.*;
import com.barberia.barberia.repository.BarberoRepository;
import com.barberia.barberia.repository.CitaRepository;
import com.barberia.barberia.services.CitaService;
import com.barberia.barberia.services.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class BarberiaApplicationTests {


    @Mock
    private CitaRepository citaRepository;

    @Mock
    private BarberoRepository barberoRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private CitaService citaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @InjectMocks
    private CitaController citaController;






    @Test
    void testSaveCita_BarberoNoExisteException() {
        Cita cita = new Cita();
        Barbero barbero = new Barbero();
        barbero.setId(2);
        cita.setBarbero1(barbero);

        when(barberoRepository.existsById(1)).thenReturn(false);

        assertThrows(BarberoNoExisteException.class, () -> citaService.saveCita(cita));

        verify(emailService, never()).sendEmailwithAttachment(any(), any(), any());
        verify(citaRepository, never()).save(cita);
    }

    @Test
    public void testGetCitaById_NotExistsq() {
        // Intentar obtener una cita con un ID que no existe
        Optional<Cita> retrievedCita = citaService.getCitaById(999);
        assertFalse(retrievedCita.isPresent());
    }




    @Test
    void testSaveCita() {
        Cita cita = new Cita();
        Barbero barbero = new Barbero();
        barbero.setId(1);
        cita.setBarbero1(barbero);

        when(barberoRepository.existsById(1)).thenReturn(false);

        assertThrows(BarberoNoExisteException.class, () -> citaService.saveCita(cita));

        // Verifica que el método sendEmailwithAttachment nunca se llama
        verify(emailService, never()).sendEmailwithAttachment(any(), any(), any());
        // Verifica que el método save nunca se llama
        verify(citaRepository, never()).save(cita);
    }

    @Test
    void testSaveCita_BarberoNoExiste() {
        // Arrange
        Cita cita = new Cita();
        Barbero barbero = new Barbero();
        barbero.setId(1);
        cita.setBarbero1(barbero);

        // Simula el caso en el que el barbero no existe
        when(barberoRepository.existsById(1)).thenReturn(false);

        // Act y Assert
        BarberoNoExisteException exception = assertThrows(BarberoNoExisteException.class, () -> citaService.saveCita(cita));

        // Verifica que el mensaje de la excepción contenga información útil
        assertTrue(exception.getMessage().contains("El barbero con id 1 no existe."));

        // Verifica que el método sendEmailwithAttachment nunca se llama
        verify(emailService, never()).sendEmailwithAttachment(any(), any(), any());
        // Verifica que el método save nunca se llama
        verify(citaRepository, never()).save(cita);
    }





    @Test
    public void testGetAllCitas() throws CorreoUsuarioNoDisponibleException {
        // Crear y guardar algunas citas
        Barbero barbero = new Barbero();
        barbero.setId(5);
        barbero.setNombre("Brayan");
        barbero.setApellido("Ortiz");
        barbero.setPuesto(1);

        Cita cita1 = new Cita();
        cita1.setBarbero("santiago");
        cita1.setCliente("Santiago");
        cita1.setEstado("activo");
        cita1.setComentarios("corte normal y delineado");
        cita1.setRecordatorio(true);
        cita1.setFinalizacion(OffsetDateTime.parse("2023-12-19T10:50:00Z"));
        cita1.setInicio(OffsetDateTime.parse("2023-12-19T09:50:00Z"));
        cita1.setBarbero1(barbero);
        cita1.setEmail("elputas0411@gmail.com");

        Cita cita2 = new Cita();
        cita2.setBarbero("santiago");
        cita2.setCliente("Santiago");
        cita2.setEstado("activo");
        cita2.setComentarios("corte normal y delineado");
        cita2.setRecordatorio(true);
        cita2.setFinalizacion(OffsetDateTime.parse("2023-12-19T10:50:00Z"));
        cita2.setInicio(OffsetDateTime.parse("2023-12-19T09:50:00Z"));
        cita2.setBarbero1(barbero);
        cita2.setEmail("elputas0411@gmail.com");

        citaService.saveCita(cita1);
        citaService.saveCita(cita2);

        // Obtener todas las citas y verificar que la lista no esté vacía
        List<Cita> citas = citaService.getAllCitas();
        assertFalse(citas.isEmpty());
    }



    @Test
    public void testGetCitaById_NotExists() {
        // Intentar obtener una cita con un ID que no existe
        Optional<Cita> retrievedCita = citaService.getCitaById(999);
        assertTrue(retrievedCita.isEmpty());
    }


}
