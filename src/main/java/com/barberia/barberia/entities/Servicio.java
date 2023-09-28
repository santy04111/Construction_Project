package com.barberia.barberia.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "\"Servicios\"")
public class Servicio {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "\"Nombre Servicio\"", nullable = false, length = Integer.MAX_VALUE)
    private String nombreServicio;

    @Column(name = "\"Valor\"", nullable = false)
    private Integer valor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreServicio() {
        return nombreServicio;
    }

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

/*
    TODO [JPA Buddy] create field to map the '\"Tipo\"' column
     Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @Column(name = "\"Tipo\"", columnDefinition = ""char"[](0, 0) not null")
    private Object tipo;
*/
}