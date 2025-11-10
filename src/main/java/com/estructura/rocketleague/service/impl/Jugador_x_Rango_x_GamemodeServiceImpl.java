package com.estructura.rocketleague.service.impl;

import com.estructura.rocketleague.dto.Jugador_x_Rango_x_GamemodeDTO;
import com.estructura.rocketleague.entity.Jugador_x_Rango_x_Gamemode;
import com.estructura.rocketleague.entity.Jugador;
import com.estructura.rocketleague.entity.Rango;
import com.estructura.rocketleague.entity.GameMode;
import com.estructura.rocketleague.repository.Jugador_x_Rango_x_GamemodeRepository;
import com.estructura.rocketleague.service.Jugador_x_Rango_x_GamemodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Jugador_x_Rango_x_GamemodeServiceImpl implements Jugador_x_Rango_x_GamemodeService {

    private final Jugador_x_Rango_x_GamemodeRepository repository;

    @Override
    public Jugador_x_Rango_x_Gamemode save(Jugador_x_Rango_x_Gamemode e) {
        return repository.save(e);
    }

    @Override
    public List<Jugador_x_Rango_x_Gamemode> listAll() {
        return repository.findAll();
    }

    @Override
    public Jugador_x_Rango_x_Gamemode update(Long id, Jugador_x_Rango_x_GamemodeDTO dto) {
        Jugador_x_Rango_x_Gamemode e = repository.findById(id).orElseThrow(() -> new RuntimeException("Not found: "+id));
        Jugador j = new Jugador(); j.setId(dto.getJugadorId());
        Rango r = new Rango(); r.setId(dto.getRangoId());
        GameMode g = new GameMode(); g.setId(dto.getGamemodeId());
        e.setJugador(j);
        e.setRango(r);
        e.setGamemode(g);
        e.setPuntuacion(dto.getPuntuacion());
        return repository.save(e);
    }

    @Override
    public List<Jugador_x_Rango_x_Gamemode> listByJugadorId(Long jugadorId) {
        return repository.findByJugador_Id(jugadorId);
    }
}
