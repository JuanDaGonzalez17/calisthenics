package com.estructura.rocketleague.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "partido")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Partido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate fechaPartido;
    private double duracion; // in minutes

    @ManyToOne
    @JoinColumn(name = "gamemode_id", nullable = false)
    private GameMode gamemode;

    @ManyToOne
    @JoinColumn(name = "estadio_id", nullable = false)
    private Estadio estadio;

    @ManyToOne
    @JoinColumn(name = "temporada_id", nullable = false)
    private Temporada temporada;

    public Partido(LocalDate fechaPartido, double duracion, GameMode gamemode, Estadio estadio, Temporada temporada) {
        this.fechaPartido = fechaPartido;
        this.duracion = duracion;
        this.gamemode = gamemode;
        this.estadio = estadio;
        this.temporada = temporada;
    }
}
