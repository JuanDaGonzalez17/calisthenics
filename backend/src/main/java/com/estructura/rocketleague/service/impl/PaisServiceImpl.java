package com.estructura.rocketleague.service.impl;
import com.estructura.rocketleague.dto.PaisDTO;
import com.estructura.rocketleague.entity.Pais;
import com.estructura.rocketleague.repository.PaisRepository;
import com.estructura.rocketleague.service.PaisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaisServiceImpl implements PaisService {

    private final PaisRepository paisRepository;

    @Override
    public Pais savePais(Pais pais) {
        return paisRepository.save(pais);
    }

    @Override
    public List<Pais> listAllPais() {
        return paisRepository.findAll();
    }

    @Override
    public Pais updatePais(Long id, PaisDTO dto) {
        Pais p = paisRepository.findById(id).orElseThrow(() -> new RuntimeException("Pais not found: "+id));
        p.setPais(dto.getPais());
        return paisRepository.save(p);
    }
}

