
package com.estructura.rocketleague.service.impl;

import com.estructura.rocketleague.dto.WheelsDTO;
import com.estructura.rocketleague.entity.Wheels;
import com.estructura.rocketleague.repository.WheelsRepository;
import com.estructura.rocketleague.service.WheelsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WheelsServiceImpl implements WheelsService {

    private final WheelsRepository wheelsRepository;

    @Override
    public Wheels saveWheels(Wheels wheels) {
        return wheelsRepository.save(wheels);
    }

    @Override
    public List<Wheels> listAllWheels() {
        return wheelsRepository.findAll();
    }

    @Override
    public Wheels updateWheels(Long id, WheelsDTO dto) {
        Wheels w = wheelsRepository.findById(id).orElseThrow(() -> new RuntimeException("Wheels not found: " + id));
        w.setWheels(dto.getWheels());
        return wheelsRepository.save(w);
    }
}