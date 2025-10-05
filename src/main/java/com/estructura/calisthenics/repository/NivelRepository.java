package com.estructura.calisthenics.repository;

import com.estructura.calisthenics.entity.Ejercicio;
import com.estructura.calisthenics.entity.Nivel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NivelRepository extends  JpaRepository<Nivel, Long>{
}