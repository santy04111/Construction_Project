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
@Table(name = "Roles")
public class Role {
    @Id
    @Column(name = "Rol", nullable = false, length = Integer.MAX_VALUE)
    private String rol;

    //TODO [JPA Buddy] generate columns from DB
}