package com.estructura.calisthenics.service.impl;

import com.estructura.calisthenics.dto.EjercicioDTO;
import com.estructura.calisthenics.dto.NivelDTO;
import com.estructura.calisthenics.entity.Ejercicio;
import com.estructura.calisthenics.entity.Nivel;
import com.estructura.calisthenics.repository.NivelRepository;
import com.estructura.calisthenics.service.NivelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NivelServiceImpl implements NivelService {

    private final NivelRepository nivelRepository;

    @Override
    public Nivel saveNivel(Nivel nivel) {
        return nivelRepository.save(nivel);
    }

    @Override
    public List<Nivel> listAllNivel() {
        return nivelRepository.findAll();
    }

    @Override
    public Nivel updateNivel(Long id, NivelDTO niveldto) {
        Nivel nivelUpdated = nivelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ejercicio no encontrado con ID: "+ id));

        nivelUpdated.setNombre(niveldto.getNombre());
        nivelUpdated.setDescripcion(niveldto.getDescripcion());

        return nivelRepository.save(nivelUpdated);
    }
}
