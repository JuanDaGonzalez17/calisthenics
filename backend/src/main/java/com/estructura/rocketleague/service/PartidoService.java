package com.estructura.rocketleague.service;

import com.estructura.rocketleague.dto.PartidoDTO;
import com.estructura.rocketleague.entity.Partido;

import java.util.List;

public interface PartidoService {
    Partido savePartido(Partido partido);
    List<Partido> listAllPartidos();
    Partido updatePartido(Long id, PartidoDTO dto);
}
