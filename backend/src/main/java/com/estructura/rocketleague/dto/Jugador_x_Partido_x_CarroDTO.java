package com.estructura.rocketleague.dto;

import lombok.Data;

@Data
public class Jugador_x_Partido_x_CarroDTO {
    private int goles;
    private int golesRecibidos;
    private int asistencias;
    private int salvadas;
    private boolean esGanador;
    private Long jugadorId;
    private Long partidoId;
    private Long carroId;
    private int tiros;
}
