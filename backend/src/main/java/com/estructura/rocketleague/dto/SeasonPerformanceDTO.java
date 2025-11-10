package com.estructura.rocketleague.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeasonPerformanceDTO {
    private Long temporadaId;
    private String temporadaName;

    private Long goles;
    private Long victorias;
    private Long partidos;
    private Long tiros;

    private Double winPct;
    private Double goalShotRatio;
    private Double score;
}

