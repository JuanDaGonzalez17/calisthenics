package com.estructura.calisthenics.service.impl;

import com.estructura.calisthenics.dto.EntrenamientoDTO;
import com.estructura.calisthenics.dto.UsuarioDTO;
import com.estructura.calisthenics.entity.Entrenamiento;
import com.estructura.calisthenics.entity.Usuario;
import com.estructura.calisthenics.repository.EntrenamientoRepository;
import com.estructura.calisthenics.repository.UsuarioRepository;
import com.estructura.calisthenics.service.EntrenamientoService;
import com.estructura.calisthenics.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EntrenamientoServiceImpl implements EntrenamientoService {

    private final EntrenamientoRepository entrenamientoRepository;

    @Override
    public Entrenamiento saveEntrenamiento(Entrenamiento entrenamiento) {
        return entrenamientoRepository.save(entrenamiento);
    }

    @Override
    public List<Entrenamiento> listAllEntrenamiento() {
        return entrenamientoRepository.findAll();
    }

    @Override
    public Entrenamiento updateEntrenamiento(Long id, EntrenamientoDTO entrenamientodto) {
        Entrenamiento entrenamientoUpdated = entrenamientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrenamiento no encontrado con ID: "+ id));

        entrenamientoUpdated.setCaloriasTotales(entrenamientodto.getCaloriasTotales());
        entrenamientoUpdated.setDia(entrenamientodto.getDia());



        return entrenamientoRepository.save(entrenamientoUpdated);
    }
}
