package com.estructura.rocketleague.service;

import com.estructura.rocketleague.dto.PaisDTO;
import com.estructura.rocketleague.entity.Pais;

import java.util.List;

public interface PaisService {
    Pais savePais(Pais pais);
    List<Pais> listAllPais();
    Pais updatePais(Long id, PaisDTO dto);
}
