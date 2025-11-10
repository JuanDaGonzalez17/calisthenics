package com.estructura.rocketleague.service.impl;

import com.estructura.rocketleague.dto.BodyDTO;
import com.estructura.rocketleague.entity.Body;
import com.estructura.rocketleague.repository.BodyRepository;
import com.estructura.rocketleague.service.BodyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BodyServiceImpl implements BodyService {

    private final BodyRepository bodyRepository;

    @Override
    public Body saveBody(Body body) {
        return bodyRepository.save(body);
    }

    @Override
    public List<Body> listAllBody() {
        return bodyRepository.findAll();
    }

    @Override
    public Body updateBody(Long id, BodyDTO dto) {
        Body b = bodyRepository.findById(id).orElseThrow(() -> new RuntimeException("Body not found: "+id));
        b.setBody(dto.getBody());
        return bodyRepository.save(b);
    }
}
