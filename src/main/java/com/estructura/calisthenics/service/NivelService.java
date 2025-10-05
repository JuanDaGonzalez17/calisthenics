package com.estructura.calisthenics.service;

import com.estructura.calisthenics.dto.NivelDTO;
import com.estructura.calisthenics.entity.Nivel;

import java.util.List;

public interface NivelService {

    public Nivel saveNivel(Nivel nivel);
    public List<Nivel> listAllNivel();
    public Nivel updateNivel(Long id, NivelDTO niveldto);
}
