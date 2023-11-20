package com.barberia.barberia.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "citas")
public class Cita {
    @Id
    @Column(name = "idcita", nullable = false)
    private Integer id;

    @Column(name = "barbero", length = Integer.MAX_VALUE)
    private String barbero;

    @Column(name = "cliente", length = Integer.MAX_VALUE)
    private String cliente;

    @Column(name = "estado", length = Integer.MAX_VALUE)
    private String estado;

    @Column(name = "comentarios", length = Integer.MAX_VALUE)
    private String comentarios;

    @Column(name = "recordatorio")
    private Boolean recordatorio;

    @Column(name = "finalizacion")
    private Timestamp finalizacion;

    @Column(name = "inicio")
    private Timestamp inicio;

}