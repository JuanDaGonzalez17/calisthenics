
package com.estructura.rocketleague.service.impl;

import com.estructura.rocketleague.dto.PartidoDTO;
import com.estructura.rocketleague.entity.Partido;
import com.estructura.rocketleague.entity.GameMode;
import com.estructura.rocketleague.entity.Estadio;
import com.estructura.rocketleague.entity.Temporada;
import com.estructura.rocketleague.repository.PartidoRepository;
import com.estructura.rocketleague.service.PartidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PartidoServiceImpl implements PartidoService {

    private final PartidoRepository partidoRepository;

    @Override
    public Partido savePartido(Partido partido) {
        return partidoRepository.save(partido);
    }

    @Override
    public List<Partido> listAllPartidos() {
        return partidoRepository.findAll();
    }

    @Override
    public Partido updatePartido(Long id, PartidoDTO dto) {
        Partido p = partidoRepository.findById(id).orElseThrow(() -> new RuntimeException("Partido not found: "+id));
        p.setFechaPartido(dto.getFechaPartido());
        p.setDuracion(dto.getDuracion());
        GameMode gm = new GameMode(); gm.setId(dto.getGamemodeId());
        Estadio est = new Estadio(); est.setId(dto.getEstadioId());
        Temporada temp = new Temporada(); temp.setId(dto.getTemporadaId());
        p.setGamemode(gm);
        p.setEstadio(est);
        p.setTemporada(temp);
        return partidoRepository.save(p);
    }
}
