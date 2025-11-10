package com.estructura.rocketleague.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "temporada")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Temporada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String temporada;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;

    public Temporada(String temporada, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        this.temporada = temporada;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }
}
