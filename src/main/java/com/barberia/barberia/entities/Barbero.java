package com.barberia.barberia.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Getter
@Setter
@Entity
@Table(name = "\"barberos\"")
public class Barbero {
    @Id
    @Column(name = "\"idbarbero\"", nullable = false)
    private Integer id;

    @Column(name = "\"nombre\"", length = Integer.MAX_VALUE)
    private String nombre;

    @Column(name = "\"apellido\"", length = Integer.MAX_VALUE)
    private String apellido;

    @Column(name = "\"puesto\"")
    private Integer puesto;

//    @JsonBackReference
    @OneToMany(mappedBy = "barbero1")
    private Set<Cita> citas = new LinkedHashSet<>();

}
