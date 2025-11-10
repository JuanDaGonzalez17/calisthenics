package com.estructura.rocketleague.service;

import com.estructura.rocketleague.dto.DecalDTO;
import com.estructura.rocketleague.entity.Decal;

import java.util.List;

public interface DecalService {
    Decal saveDecal(Decal decal);
    List<Decal> listAllDecals();
    Decal updateDecal(Long id, DecalDTO dto);
}
