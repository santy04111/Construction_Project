package com.barberia.barberia.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "\"Turnos\"")
public class Turno {
    @Id
    @Column(name = "\"IdTurnos\"", nullable = false, length = Integer.MAX_VALUE)
    private String idTurnos;

    @Column(name = "\"Nombre\"", nullable = false, length = Integer.MAX_VALUE)
    private String nombre;

    @Column(name = "\"Hora\"", nullable = false, length = Integer.MAX_VALUE)
    private String hora;

    @Column(name = "\"Servicio\"", nullable = false, length = Integer.MAX_VALUE)
    private String servicio;

}