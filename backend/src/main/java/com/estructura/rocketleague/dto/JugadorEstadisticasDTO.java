package com.estructura.rocketleague.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JugadorEstadisticasDTO {
    private Long jugadorId;
    private String userName;
    private Long goles;
    private Long asistencias;
    private Long salvadas;
    private Long victorias;
    private Long partidosJugados;
    private Double winPercentage;
    private Long tiros;
    private Double goalShotRatio;
}
