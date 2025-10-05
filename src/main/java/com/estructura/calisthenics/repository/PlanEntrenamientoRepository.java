package com.estructura.calisthenics.repository;

import com.estructura.calisthenics.entity.PlanEntrenamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanEntrenamientoRepository extends  JpaRepository<PlanEntrenamiento, Long>{
}