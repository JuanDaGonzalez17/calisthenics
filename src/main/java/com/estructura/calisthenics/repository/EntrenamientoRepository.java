package com.estructura.calisthenics.repository;

import com.estructura.calisthenics.entity.Ejercicio;
import com.estructura.calisthenics.entity.Entrenamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntrenamientoRepository extends  JpaRepository<Entrenamiento, Long>{
}