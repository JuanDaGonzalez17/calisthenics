package com.estructura.rocketleague.service;

import com.estructura.rocketleague.dto.BodyDTO;
import com.estructura.rocketleague.entity.Body;
import java.util.List;

public interface BodyService {
    Body saveBody(Body body);
    List<Body> listAllBody();
    Body updateBody(Long id, BodyDTO dto);
}

