package com.estructura.rocketleague.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "jugador_x_rango_x_gamemode")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Jugador_x_Rango_x_Gamemode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "jugador_id", nullable = false)
    private Jugador jugador;

    @ManyToOne
    @JoinColumn(name = "rango_id", nullable = false)
    private Rango rango;

    @ManyToOne
    @JoinColumn(name = "gamemode_id", nullable = false)
    private GameMode gamemode;

    @Column(nullable = false)
    private double puntuacion;

    public Jugador_x_Rango_x_Gamemode(Jugador jugador, Rango rango, GameMode gamemode, double puntuacion) {
        this.jugador = jugador;
        this.rango = rango;
        this.gamemode = gamemode;
        this.puntuacion = puntuacion;
    }
}
