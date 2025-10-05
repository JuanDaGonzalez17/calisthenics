package com.estructura.calisthenics.service.impl;

import com.estructura.calisthenics.dto.EjercicioDTO;
import com.estructura.calisthenics.entity.Ejercicio;
import com.estructura.calisthenics.repository.EjercicioRepository;
import com.estructura.calisthenics.service.EjercicioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EjercicioServiceImpl implements EjercicioService {

    private final EjercicioRepository ejercicioRepository;

    @Override
    public Ejercicio saveEjercicio(Ejercicio ejercicio) {
        return ejercicioRepository.save(ejercicio);
    }

    @Override
    public List<Ejercicio> listAllEjercicios() {
        return ejercicioRepository.findAll();
    }

    @Override
    public Ejercicio updateEjercicio(Long id, EjercicioDTO ejerciciodto) {
        Ejercicio ejercicioUpdated = ejercicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ejercicio no encontrado con ID: "+ id));

        ejercicioUpdated.setNombre(ejerciciodto.getNombre());
        ejercicioUpdated.setDescripcion(ejerciciodto.getDescripcion());

        return ejercicioRepository.save(ejercicioUpdated);
    }
}
