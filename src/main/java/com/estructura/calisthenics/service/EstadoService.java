package com.estructura.calisthenics.service;

import com.estructura.calisthenics.dto.EntrenamientoDTO;
import com.estructura.calisthenics.dto.EstadoDTO;
import com.estructura.calisthenics.entity.Entrenamiento;
import com.estructura.calisthenics.entity.Estado;

import java.util.List;

public interface EstadoService {

    public Estado saveEstado(Estado estado);
    public List<Estado> listAllEstado();
    public Estado updateEstado(Long id, EstadoDTO estadodto);
}
