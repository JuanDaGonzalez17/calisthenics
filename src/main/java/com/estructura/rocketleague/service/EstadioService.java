package com.estructura.rocketleague.service;

import com.estructura.rocketleague.dto.EstadioDTO;
import com.estructura.rocketleague.entity.Estadio;

import java.util.List;

public interface EstadioService {
    Estadio saveEstadio(Estadio estadio);
    List<Estadio> listAllEstadios();
    Estadio updateEstadio(Long id, EstadioDTO dto);
}
