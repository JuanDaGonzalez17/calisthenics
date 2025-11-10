package com.estructura.rocketleague.service;

import com.estructura.rocketleague.dto.JugadorEstadisticasDTO;
import com.estructura.rocketleague.dto.Jugador_x_Partido_x_CarroDTO;
import com.estructura.rocketleague.dto.LeaderboardEntryDTO;
import com.estructura.rocketleague.dto.CarPerformanceDTO;
import com.estructura.rocketleague.entity.Jugador_x_Partido_x_Carro;

import java.util.List;

public interface Jugador_x_Partido_x_CarroService {
    Jugador_x_Partido_x_Carro save(Jugador_x_Partido_x_Carro e);
    List<Jugador_x_Partido_x_Carro> listAll();
    Jugador_x_Partido_x_Carro update(Long id, Jugador_x_Partido_x_CarroDTO dto);

    // Método unificado que acepta tanto ID como username
    List<JugadorEstadisticasDTO> getEstadisticasFiltradas(
            String identificador, // puede ser ID numérico o username
            Long idDecal,
            Long idBoost,
            Long idBody,
            Long idWheels,
            Long idEstadio,
            Long idGamemode,
            Long idTemporada
    );

    // Leaderboard: solo filtros permitidos: idGamemode, idPais y limit
    List<LeaderboardEntryDTO> getLeaderboard(
            Long idGamemode,
            Long idPais,
            Integer limit
    );

    // Devuelve el top N de carros para un jugador específico según win% y goal-shot ratio
    List<CarPerformanceDTO> getTopNCarsForJugador(Long idJugador, Integer limit);

    // Rendimiento por temporada para un jugador: lista de temporadas con métricas agregadas
    List<com.estructura.rocketleague.dto.SeasonPerformanceDTO> getSeasonPerformanceByJugador(Long idJugador, Integer minMatches, Integer limit);
}
