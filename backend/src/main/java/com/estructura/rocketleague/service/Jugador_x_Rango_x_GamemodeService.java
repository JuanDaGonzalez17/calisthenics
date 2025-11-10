package com.estructura.rocketleague.service;

import com.estructura.rocketleague.dto.Jugador_x_Rango_x_GamemodeDTO;
import com.estructura.rocketleague.entity.Jugador_x_Rango_x_Gamemode;

import java.util.List;

public interface Jugador_x_Rango_x_GamemodeService {
    Jugador_x_Rango_x_Gamemode save(Jugador_x_Rango_x_Gamemode e);
    List<Jugador_x_Rango_x_Gamemode> listAll();
    Jugador_x_Rango_x_Gamemode update(Long id, Jugador_x_Rango_x_GamemodeDTO dto);
    List<Jugador_x_Rango_x_Gamemode> listByJugadorId(Long jugadorId);
}
