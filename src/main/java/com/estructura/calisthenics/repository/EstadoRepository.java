package com.estructura.calisthenics.repository;

import com.estructura.calisthenics.entity.Entrenamiento;
import com.estructura.calisthenics.entity.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoRepository extends  JpaRepository<Estado, Long>{
}