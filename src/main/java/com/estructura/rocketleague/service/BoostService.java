package com.estructura.rocketleague.service;

import com.estructura.rocketleague.dto.BoostDTO;
import com.estructura.rocketleague.entity.Boost;

import java.util.List;

public interface BoostService {
    Boost saveBoost(Boost boost);
    List<Boost> listAllBoost();
    Boost updateBoost(Long id, BoostDTO dto);
}
