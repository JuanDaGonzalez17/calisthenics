package com.estructura.rocketleague.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarPerformanceDTO {
    private Long carroId;
    private Long bodyId;
    private Long decalId;
    private Long wheelsId;
    private Long boostId;

    private Long goles;
    private Long victorias;
    private Long partidos;
    private Long tiros;

    private Double winPct;
    private Double goalShotRatio;
    private Double score; // puntuación combinada para ordenar
}

