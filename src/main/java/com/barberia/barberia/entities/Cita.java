package com.barberia.barberia.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Data
@Getter
@Setter
@Entity
@Table(name = "\"citas\"")
public class Cita {
    @Id
    @Column(name = "\"idcita\"", nullable = false)
    private Integer id;

    @Column(name = "\"barbero\"", length = Integer.MAX_VALUE)
    private String barbero;

    @Column(name = "\"cliente\"", length = Integer.MAX_VALUE)
    private String cliente;

    @Column(name = "\"estado\"", length = Integer.MAX_VALUE)
    private String estado;

    @Column(name = "\"comentarios\"", length = Integer.MAX_VALUE)
    private String comentarios;

    @Column(name = "\"recordatorio\"")
    private Boolean recordatorio;

    @Column(name = "\"finalizacion\"")
    private OffsetDateTime finalizacion;

    @Column(name = "\"inicio\"")
    private OffsetDateTime inicio;

//    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "barbero_id")
    private Barbero barbero1;

}