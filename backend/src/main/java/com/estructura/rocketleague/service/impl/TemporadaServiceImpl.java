package com.estructura.rocketleague.service.impl;

import com.estructura.rocketleague.dto.TemporadaDTO;
import com.estructura.rocketleague.entity.Temporada;
import com.estructura.rocketleague.repository.TemporadaRepository;
import com.estructura.rocketleague.service.TemporadaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TemporadaServiceImpl implements TemporadaService {

    private final TemporadaRepository temporadaRepository;

    @Override
    public Temporada saveTemporada(Temporada temporada) {
        return temporadaRepository.save(temporada);
    }

    @Override
    public List<Temporada> listAllTemporadas() {
        return temporadaRepository.findAll();
    }

    @Override
    public Temporada updateTemporada(Long id, TemporadaDTO dto) {
        Temporada t = temporadaRepository.findById(id).orElseThrow(() -> new RuntimeException("Temporada not found: "+id));
        t.setTemporada(dto.getTemporada());
        t.setFechaInicio(dto.getFechaInicio());
        t.setFechaFin(dto.getFechaFin());
        return temporadaRepository.save(t);
    }
}
