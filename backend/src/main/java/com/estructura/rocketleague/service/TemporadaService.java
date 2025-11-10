package com.estructura.rocketleague.service;

import com.estructura.rocketleague.dto.TemporadaDTO;
import com.estructura.rocketleague.entity.Temporada;

import java.util.List;

public interface TemporadaService {
    Temporada saveTemporada(Temporada temporada);
    List<Temporada> listAllTemporadas();
    Temporada updateTemporada(Long id, TemporadaDTO dto);
}
