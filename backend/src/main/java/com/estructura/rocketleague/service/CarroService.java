package com.estructura.rocketleague.service;

import com.estructura.rocketleague.dto.CarroDTO;
import com.estructura.rocketleague.entity.Carro;

import java.util.List;

public interface CarroService {
    Carro saveCarro(Carro carro);
    List<Carro> listAllCarros();
    Carro updateCarro(Long id, CarroDTO dto);
}
