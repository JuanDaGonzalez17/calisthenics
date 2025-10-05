package com.estructura.calisthenics.service;

import java.util.List;

import com.estructura.calisthenics.dto.EjercicioDTO;
import com.estructura.calisthenics.entity.Ejercicio;

public interface EjercicioService {

    public Ejercicio saveEjercicio(Ejercicio ejercicio);
    public List<Ejercicio> listAllEjercicios();
    public Ejercicio updateEjercicio(Long id, EjercicioDTO ejerciciodto);
}
