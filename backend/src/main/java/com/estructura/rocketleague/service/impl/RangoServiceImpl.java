package com.estructura.rocketleague.service.impl;

import com.estructura.rocketleague.dto.RangoDTO;
import com.estructura.rocketleague.entity.Rango;
import com.estructura.rocketleague.repository.RangoRepository;
import com.estructura.rocketleague.service.RangoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RangoServiceImpl implements RangoService {

    private final RangoRepository rangoRepository;

    @Override
    public Rango saveRango(Rango rango) {
        return rangoRepository.save(rango);
    }

    @Override
    public List<Rango> listAllRangos() {
        return rangoRepository.findAll();
    }

    @Override
    public Rango updateRango(Long id, RangoDTO dto) {
        Rango r = rangoRepository.findById(id).orElseThrow(() -> new RuntimeException("Rango not found: "+id));
        r.setRango(dto.getRango());
        r.setDivision(dto.getDivision());
        return rangoRepository.save(r);
    }
}
