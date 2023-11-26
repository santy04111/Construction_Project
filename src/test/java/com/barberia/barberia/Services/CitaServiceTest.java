package com.barberia.barberia.Services;

import com.barberia.barberia.services.CitaService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CitaServiceTest {

    @BeforeEach
    void setUp(){

    }
    @AfterEach
    void tearDown(){

    }



    @Autowired
    private CitaService citaService;

//    @Test
//    void crearNuevaCita() {
//
//        Cita nuevaCita = new Cita();
//        nuevaCita.setBarbero("Barbero de prueba");
//        nuevaCita.setCliente("Cliente de prueba");
//        nuevaCita.setEstado("Pendiente");
//        nuevaCita.setComentarios("Comentarios de prueba");
//        nuevaCita.setRecordatorio(true);
//        nuevaCita.setInicio(new Timestamp(System.currentTimeMillis()));
//
//        Assertions.assertEquals(nuevaCita, citaService.crearNuevaCita(nuevaCita));
//
//      //  long idCita = citaService.crearNuevaCita(nuevaCita);
//
//
//        //assertNotNull(idCita, "El identificador de la cita no debe ser nulo");
//       // assertTrue(idCita > 0, "El identificador de la cita debe ser mayor que 0");
//
//
//    }
}