package com.estructura.rocketleague.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rango_x_gamemode")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rango_x_Gamemode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "rango_id", nullable = false)
    private Rango rango;

    @ManyToOne
    @JoinColumn(name = "gamemode_id", nullable = false)
    private GameMode gamemode;

    Long puntuacion_inicial;
    Long puntuacion_final;

    public Rango_x_Gamemode(Rango rango, GameMode gamemode, Long puntuacion_inicial, Long puntuacion_final) {
        this.puntuacion_inicial = puntuacion_inicial;
        this.puntuacion_final = puntuacion_final;
        this.rango = rango;
        this.gamemode = gamemode;
    }


}
