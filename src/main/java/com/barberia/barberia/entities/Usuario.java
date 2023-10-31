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
@Table(name = "Usuario")
public class Usuario {
    @Id
    @Column(name = "Usuario", nullable = false, length = Integer.MAX_VALUE)
    private String usuario;

    @Column(name = "Email", length = Integer.MAX_VALUE)
    private String email;

    @Column(name = "Password", length = Integer.MAX_VALUE)
    private String password;

    @Column(name = "Rol")
    private Integer rol;

}