package com.estructura.rocketleague.service;

import com.estructura.rocketleague.dto.WheelsDTO;
import com.estructura.rocketleague.entity.Wheels;

import java.util.List;

public interface WheelsService {
    Wheels saveWheels(Wheels wheels);
    List<Wheels> listAllWheels();
    Wheels updateWheels(Long id, WheelsDTO dto);
}
