package com.estructura.rocketleague.service.impl;

import com.estructura.rocketleague.dto.EstadioDTO;
import com.estructura.rocketleague.entity.Estadio;
import com.estructura.rocketleague.repository.EstadioRepository;
import com.estructura.rocketleague.service.EstadioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstadioServiceImpl implements EstadioService {

    private final EstadioRepository estadioRepository;

    @Override
    public Estadio saveEstadio(Estadio estadio) {
        return estadioRepository.save(estadio);
    }

    @Override
    public List<Estadio> listAllEstadios() {
        return estadioRepository.findAll();
    }

    @Override
    public Estadio updateEstadio(Long id, EstadioDTO dto) {
        Estadio e = estadioRepository.findById(id).orElseThrow(() -> new RuntimeException("Estadio not found: "+id));
        e.setEstadio(dto.getEstadio());
        return estadioRepository.save(e);
    }
}
