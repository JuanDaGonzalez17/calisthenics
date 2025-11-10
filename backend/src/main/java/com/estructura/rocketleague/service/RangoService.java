package com.estructura.rocketleague.service;

import com.estructura.rocketleague.dto.RangoDTO;
import com.estructura.rocketleague.entity.Rango;

import java.util.List;

public interface RangoService {
    Rango saveRango(Rango rango);
    List<Rango> listAllRangos();
    Rango updateRango(Long id, RangoDTO dto);
}
