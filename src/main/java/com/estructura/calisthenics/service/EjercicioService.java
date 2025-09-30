package com.estructura.calisthenics.service;

import java.util.List;

import com.estructura.calisthenics.entity.Ejercicio;

public interface EjercicioService {

    public Ejercicio saveEjercicio(Ejercicio ejercicio);
    public List<Ejercicio> listAllEjercicios();
}
