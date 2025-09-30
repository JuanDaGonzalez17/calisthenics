package com.estructura.calisthenics.repository;

import  com.estructura.calisthenics.entity.Ejercicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EjercicioRepository  extends  JpaRepository<Ejercicio, Long>{
}