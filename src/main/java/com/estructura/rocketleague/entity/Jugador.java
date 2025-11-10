package com.estructura.rocketleague.entity;
import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "jugador")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Jugador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;
    private String email;
    private String telefono;
    private String userName;
    private LocalDate fechaNacimiento;

    @ManyToOne
    @JoinColumn(name = "pais_id", nullable = true)
    private Pais pais;

    public Jugador(String nombre, String email, String telefono, String userName, LocalDate fechaNacimiento, Pais pais) {
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.userName = userName;
        this.fechaNacimiento = fechaNacimiento;
        this.pais = pais;
    }
}
