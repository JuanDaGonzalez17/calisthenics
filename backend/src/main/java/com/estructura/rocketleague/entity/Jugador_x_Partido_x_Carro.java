package com.estructura.rocketleague.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "jugador_x_partido_x_carro")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Jugador_x_Partido_x_Carro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int goles;
    private int golesRecibidos;
    private int asistencias;
    private int salvadas;
    private boolean esGanador;
    private int tiros;

    @ManyToOne
    @JoinColumn(name = "jugador_id", nullable = false)
    private Jugador jugador;

    @ManyToOne
    @JoinColumn(name = "partido_id", nullable = false)
    private Partido partido;

    @ManyToOne
    @JoinColumn(name = "carro_id", nullable = false)
    private Carro carro;

    public Jugador_x_Partido_x_Carro(int goles, int golesRecibidos, int asistencias, int salvadas, boolean esGanador, Jugador jugador, Partido partido, Carro carro, int tiros) {
        this.goles = goles;
        this.golesRecibidos = golesRecibidos;
        this.asistencias = asistencias;
        this.salvadas = salvadas;
        this.esGanador = esGanador;
        this.jugador = jugador;
        this.partido = partido;
        this.carro = carro;
        this.tiros = tiros;
    }
}
