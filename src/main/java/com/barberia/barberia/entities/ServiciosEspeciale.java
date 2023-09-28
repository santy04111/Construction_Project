package com.barberia.barberia.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "\"Servicios Especiales\"")
public class ServiciosEspeciale {
    @Id
    @Column(name = "\"Id\"", nullable = false)
    private Integer id;

    @Column(name = "\"Disponibilidad\"", nullable = false)
    private Boolean disponibilidad = false;
    @Column(name = "\"Estado\"", nullable = false)
    private Boolean estado = false;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(Boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

/*
    TODO [JPA Buddy] create field to map the '\"DuracionServicio\"' column
     Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @Column(name = "\"DuracionServicio\"", columnDefinition = "interval(0, 0) not null")
    private Object duracionServicio;
*/
}