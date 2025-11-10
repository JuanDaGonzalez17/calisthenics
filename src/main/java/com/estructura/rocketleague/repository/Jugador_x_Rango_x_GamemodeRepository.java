package com.estructura.rocketleague.repository;

import com.estructura.rocketleague.entity.Jugador_x_Rango_x_Gamemode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Jugador_x_Rango_x_GamemodeRepository extends JpaRepository<Jugador_x_Rango_x_Gamemode, Long> {
    // Devuelve todas las entradas de rango por gamemode para un jugador específico
    List<Jugador_x_Rango_x_Gamemode> findByJugador_Id(Long jugadorId);

    // Devuelve filas (jugadorId, userName, puntuacion) para el leaderboard filtrado por gamemode y pais
    @Query(value = """
        SELECT jr.jugador.id, jr.jugador.userName, jr.puntuacion
        FROM Jugador_x_Rango_x_Gamemode jr
        WHERE jr.gamemode.id = :idGamemode
        AND (:idPais IS NULL OR jr.jugador.pais.id = :idPais)
        ORDER BY jr.puntuacion DESC
    """, nativeQuery = false)
    List<Object[]> getLeaderboardByGamemodeRaw(
            @Param("idGamemode") Long idGamemode,
            @Param("idPais") Long idPais
    );
}