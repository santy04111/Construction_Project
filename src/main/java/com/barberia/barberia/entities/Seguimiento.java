package com.barberia.barberia.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Data

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "\"seguimiento\"")
public class Seguimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "\"idguid\"", nullable = false)
    private UUID idguid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"codigocita\"", nullable = false)
    private Cita cita;

    @Column(name = "\"codigoestado\"", nullable = false)
    private String codigoEstado;

    @Column(name = "\"vigente\"", nullable = false)
    private Integer vigente;

    @Column(name = "\"fecha_seguimiento\"", nullable = false)
    private OffsetDateTime fechaSeguimiento;
}
