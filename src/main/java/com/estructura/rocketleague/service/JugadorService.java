package com.estructura.rocketleague.service;

import com.estructura.rocketleague.dto.JugadorDTO;
import com.estructura.rocketleague.entity.Jugador;

import java.util.List;

public interface JugadorService {

    public Jugador saveJugador(Jugador jugador);
    public List<Jugador> listAllJugadores();
    public Jugador updateJugador(Long id, JugadorDTO jugadordto);
}
