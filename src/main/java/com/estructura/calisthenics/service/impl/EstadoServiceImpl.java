package com.estructura.calisthenics.service.impl;

import com.estructura.calisthenics.dto.EntrenamientoDTO;
import com.estructura.calisthenics.dto.EstadoDTO;
import com.estructura.calisthenics.entity.Entrenamiento;
import com.estructura.calisthenics.entity.Estado;
import com.estructura.calisthenics.repository.EntrenamientoRepository;
import com.estructura.calisthenics.repository.EstadoRepository;
import com.estructura.calisthenics.service.EntrenamientoService;
import com.estructura.calisthenics.service.EstadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstadoServiceImpl implements EstadoService {

    private final EstadoRepository estadoRepository;

    @Override
    public Estado saveEstado(Estado estado) {
        return estadoRepository.save(estado);
    }

    @Override
    public List<Estado> listAllEstado() {
        return estadoRepository.findAll();
    }

    @Override
    public Estado updateEstado(Long id, EstadoDTO estadodto) {
        Estado estadoUpdated = estadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado con ID: "+ id));

        estadoUpdated.setNombre(estadodto.getNombre());



        return estadoRepository.save(estadoUpdated);
    }
}
