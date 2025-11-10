package com.estructura.rocketleague.service.impl;

import com.estructura.rocketleague.dto.JugadorDTO;
import com.estructura.rocketleague.entity.Jugador;
import com.estructura.rocketleague.entity.Jugador_x_Partido_x_Carro;
import com.estructura.rocketleague.repository.JugadorRepository;
import com.estructura.rocketleague.repository.Jugador_x_Partido_x_CarroRepository;
import com.estructura.rocketleague.service.JugadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JugadorServiceImpl implements JugadorService {

    private final JugadorRepository jugadorRepository;

    @Autowired
    private Jugador_x_Partido_x_CarroRepository jpcRepository;

    @Override
    public Jugador saveJugador(Jugador jugador) {
        return jugadorRepository.save(jugador);
    }

    @Override
    public List<Jugador> listAllJugadores() {
        return jugadorRepository.findAll();
    }

    @Override
    public Jugador updateJugador(Long id, JugadorDTO jugadordto) {
        Jugador jugadorUpdated = jugadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado con ID: "+ id));

        jugadorUpdated.setNombre(jugadordto.getNombre());
        jugadorUpdated.setEmail(jugadordto.getEmail());
        jugadorUpdated.setTelefono(jugadordto.getTelefono());
        jugadorUpdated.setUserName(jugadordto.getUserName());
        jugadorUpdated.setFechaNacimiento(jugadordto.getFechaNacimiento());

        return jugadorRepository.save(jugadorUpdated);
    }


}
