package com.estructura.calisthenics.service;

import com.estructura.calisthenics.dto.EntrenamientoDTO;
import com.estructura.calisthenics.dto.PlanEntrenamientoDTO;
import com.estructura.calisthenics.entity.Entrenamiento;
import com.estructura.calisthenics.entity.PlanEntrenamiento;

import java.util.List;

public interface PlanEntrenamientoService {

    public PlanEntrenamiento savePlanEntrenamiento(PlanEntrenamiento planentrenamiento);
    public List<PlanEntrenamiento> listAllPlanEntrenamiento();
    public PlanEntrenamiento updatePlanEntrenamiento(Long id, PlanEntrenamientoDTO planentrenamientodto);
}
