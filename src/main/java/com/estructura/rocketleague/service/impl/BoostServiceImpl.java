package com.estructura.rocketleague.service.impl;

import com.estructura.rocketleague.dto.BoostDTO;
import com.estructura.rocketleague.entity.Boost;
import com.estructura.rocketleague.repository.BoostRepository;
import com.estructura.rocketleague.service.BoostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoostServiceImpl implements BoostService {

    private final BoostRepository boostRepository;

    @Override
    public Boost saveBoost(Boost boost) {
        return boostRepository.save(boost);
    }

    @Override
    public List<Boost> listAllBoost() {
        return boostRepository.findAll();
    }

    @Override
    public Boost updateBoost(Long id, BoostDTO dto) {
        Boost b = boostRepository.findById(id).orElseThrow(() -> new RuntimeException("Boost not found: "+id));
        b.setBoost(dto.getBoost());
        return boostRepository.save(b);
    }
}
