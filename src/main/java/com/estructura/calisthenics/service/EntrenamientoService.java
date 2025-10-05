package com.estructura.calisthenics.service;

import com.estructura.calisthenics.dto.EjercicioDTO;
import com.estructura.calisthenics.dto.EntrenamientoDTO;
import com.estructura.calisthenics.entity.Ejercicio;
import com.estructura.calisthenics.entity.Entrenamiento;

import java.util.List;

public interface EntrenamientoService {

    public Entrenamiento saveEntrenamiento(Entrenamiento entrenamiento);
    public List<Entrenamiento> listAllEntrenamiento();
    public Entrenamiento updateEntrenamiento(Long id, EntrenamientoDTO entrenamientodto);
}
