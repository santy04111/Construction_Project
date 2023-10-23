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
@Table(name = "\"Citas\"")
public class Cita {
    @Id
    @Column(name = "\"ID cita\"", nullable = false)
    private Integer id;

    @Column(name = "\"Inicio\"", length = Integer.MAX_VALUE)
    private String inicio;

    @Column(name = "\"Finalizacion\"", length = Integer.MAX_VALUE)
    private String finalizacion;

    @Column(name = "\"Barbero\"", length = Integer.MAX_VALUE)
    private String barbero;

    @Column(name = "\"Cliente\"", length = Integer.MAX_VALUE)
    private String cliente;

    @Column(name = "\"Estada de la cita\"", length = Integer.MAX_VALUE)
    private String estadaDeLaCita;

    @Column(name = "\"Notas o comentarios\"", length = Integer.MAX_VALUE)
    private String notasOComentarios;

    @Column(name = "\"Recordatorio\"")
    private Boolean recordatorio;

}