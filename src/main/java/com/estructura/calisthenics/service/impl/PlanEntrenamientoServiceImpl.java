package com.estructura.calisthenics.service.impl;

import com.estructura.calisthenics.dto.PlanEntrenamientoDTO;
import com.estructura.calisthenics.entity.Entrenamiento;
import com.estructura.calisthenics.entity.PlanEntrenamiento;
import com.estructura.calisthenics.service.PlanEntrenamientoService;
import com.estructura.calisthenics.repository.PlanEntrenamientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanEntrenamientoServiceImpl implements PlanEntrenamientoService {

    private final PlanEntrenamientoRepository planEntrenamientoRepository;

    @Override
    public PlanEntrenamiento savePlanEntrenamiento(PlanEntrenamiento planEntrenamiento) {
        return planEntrenamientoRepository.save(planEntrenamiento);
    }

    @Override
    public List<PlanEntrenamiento> listAllPlanEntrenamiento() {
        return planEntrenamientoRepository.findAll();
    }

    @Override
    public PlanEntrenamiento updatePlanEntrenamiento(Long id, PlanEntrenamientoDTO planEntrenamientodto) {
        PlanEntrenamiento planEntrenamientoUpdated = planEntrenamientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan de Entrenamiento no encontrado con ID: "+ id));

        planEntrenamientoUpdated.setNombre(planEntrenamientodto.getNombre());
        planEntrenamientoUpdated.setDescripcion(planEntrenamientodto.getDescripcion());
        planEntrenamientoUpdated.setObjetivo(planEntrenamientodto.getObjetivo());



        return planEntrenamientoRepository.save(planEntrenamientoUpdated);
    }
}
