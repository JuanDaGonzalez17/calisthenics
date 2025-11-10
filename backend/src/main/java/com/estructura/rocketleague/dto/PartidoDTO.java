package com.estructura.rocketleague.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PartidoDTO {
    private LocalDate fechaPartido;
    private double duracion;
    private Long gamemodeId;
    private Long estadioId;
    private Long temporadaId;
}
