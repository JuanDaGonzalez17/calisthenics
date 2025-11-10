package com.estructura.rocketleague.dto;

import lombok.Data;

@Data
public class Jugador_x_Rango_x_GamemodeDTO {
    private Long jugadorId;
    private Long rangoId;
    private Long gamemodeId;
    private double puntuacion;
}
