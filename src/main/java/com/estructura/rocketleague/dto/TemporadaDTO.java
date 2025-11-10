package com.estructura.rocketleague.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TemporadaDTO {
    private String temporada;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
}
