package com.estructura.rocketleague.service.impl;

import com.estructura.rocketleague.dto.CarroDTO;
import com.estructura.rocketleague.entity.Carro;
import com.estructura.rocketleague.entity.Body;
import com.estructura.rocketleague.entity.Decal;
import com.estructura.rocketleague.entity.Wheels;
import com.estructura.rocketleague.entity.Boost;
import com.estructura.rocketleague.repository.CarroRepository;
import com.estructura.rocketleague.service.CarroService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarroServiceImpl implements CarroService {

    private final CarroRepository carroRepository;

    @Override
    public Carro saveCarro(Carro carro) {
        return carroRepository.save(carro);
    }

    @Override
    public List<Carro> listAllCarros() {
        return carroRepository.findAll();
    }

    @Override
    public Carro updateCarro(Long id, CarroDTO dto) {
        Carro c = carroRepository.findById(id).orElseThrow(() -> new RuntimeException("Carro not found: "+id));
        Body body = new Body(); body.setId(dto.getBodyId());
        Decal decal = new Decal(); decal.setId(dto.getDecalId());
        Wheels wheels = new Wheels(); wheels.setId(dto.getWheelsId());
        Boost boost = new Boost(); boost.setId(dto.getBoostId());
        c.setBody(body);
        c.setDecal(decal);
        c.setWheels(wheels);
        c.setBoost(boost);
        return carroRepository.save(c);
    }
}
