package com.estructura.rocketleague.service;

import com.estructura.rocketleague.dto.Rango_x_GamemodeDTO;
import com.estructura.rocketleague.entity.Rango_x_Gamemode;

import java.util.List;

public interface Rango_x_GamemodeService {
    Rango_x_Gamemode save(Rango_x_Gamemode e);
    List<Rango_x_Gamemode> listAll();
    Rango_x_Gamemode update(Long id, Rango_x_GamemodeDTO dto);
    List<Rango_x_Gamemode> listByRangoId(Long rangoId);
    List<Rango_x_Gamemode> listByGamemodeId(Long gamemodeId);
    List<Rango_x_Gamemode> listByRangoAndGamemode(Long rangoId, Long gamemodeId);
}

