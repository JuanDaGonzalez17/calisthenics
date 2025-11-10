package com.estructura.rocketleague.service.impl;

import com.estructura.rocketleague.dto.Rango_x_GamemodeDTO;
import com.estructura.rocketleague.entity.Rango_x_Gamemode;
import com.estructura.rocketleague.entity.Rango;
import com.estructura.rocketleague.entity.GameMode;
import com.estructura.rocketleague.repository.Rango_x_GamemodeRepository;
import com.estructura.rocketleague.service.Rango_x_GamemodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Rango_x_GamemodeServiceImpl implements Rango_x_GamemodeService {

    private final Rango_x_GamemodeRepository repository;

    @Override
    public Rango_x_Gamemode save(Rango_x_Gamemode e) {
        return repository.save(e);
    }

    @Override
    public List<Rango_x_Gamemode> listAll() {
        return repository.findAll();
    }

    @Override
    public Rango_x_Gamemode update(Long id, Rango_x_GamemodeDTO dto) {
        Rango_x_Gamemode e = repository.findById(id).orElseThrow(() -> new RuntimeException("Not found: "+id));
        Rango r = new Rango(); r.setId(dto.getRangoId());
        GameMode g = new GameMode(); g.setId(dto.getGamemodeId());
        e.setRango(r);
        e.setGamemode(g);
        e.setPuntuacion_inicial(dto.getPuntuacionInicial());
        e.setPuntuacion_final(dto.getPuntuacionFinal());
        return repository.save(e);
    }

    @Override
    public List<Rango_x_Gamemode> listByRangoId(Long rangoId) {
        return repository.findByRango_Id(rangoId);
    }

    @Override
    public List<Rango_x_Gamemode> listByGamemodeId(Long gamemodeId) {
        return repository.findByGamemode_Id(gamemodeId);
    }

    @Override
    public List<Rango_x_Gamemode> listByRangoAndGamemode(Long rangoId, Long gamemodeId) {
        return repository.findByRango_IdAndGamemode_Id(rangoId, gamemodeId);
    }
}
