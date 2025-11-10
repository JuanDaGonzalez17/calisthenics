package com.estructura.rocketleague.service.impl;

import com.estructura.rocketleague.dto.DecalDTO;
import com.estructura.rocketleague.entity.Decal;
import com.estructura.rocketleague.repository.DecalRepository;
import com.estructura.rocketleague.service.DecalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DecalServiceImpl implements DecalService {

    private final DecalRepository decalRepository;

    @Override
    public Decal saveDecal(Decal decal) {
        return decalRepository.save(decal);
    }

    @Override
    public List<Decal> listAllDecals() {
        return decalRepository.findAll();
    }

    @Override
    public Decal updateDecal(Long id, DecalDTO dto) {
        Decal d = decalRepository.findById(id).orElseThrow(() -> new RuntimeException("Decal not found: "+id));
        d.setDecal(dto.getDecal());
        return decalRepository.save(d);
    }
}
