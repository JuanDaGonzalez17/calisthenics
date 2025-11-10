package com.estructura.rocketleague.repository;

import com.estructura.rocketleague.entity.Jugador_x_Partido_x_Carro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Jugador_x_Partido_x_CarroRepository extends JpaRepository<Jugador_x_Partido_x_Carro, Long> {

    @Query(value = """
        SELECT j.id, j.userName,
               COALESCE(SUM(jpc.goles), 0L),
               COALESCE(SUM(jpc.asistencias), 0L),
               COALESCE(SUM(jpc.salvadas), 0L),
               COALESCE(SUM(CASE WHEN jpc.esGanador = true THEN 1 ELSE 0 END), 0L),
               COALESCE(COUNT(DISTINCT p.id), 0L),
               COALESCE(SUM(jpc.tiros), 0L)
        FROM Jugador_x_Partido_x_Carro jpc
        JOIN jpc.jugador j
        JOIN jpc.partido p
        JOIN jpc.carro c
        WHERE j.id = :idJugador
        AND (:idDecal IS NULL OR c.decal.id = :idDecal)
        AND (:idBoost IS NULL OR c.boost.id = :idBoost)
        AND (:idBody IS NULL OR c.body.id = :idBody)
        AND (:idWheels IS NULL OR c.wheels.id = :idWheels)
        AND (:idEstadio IS NULL OR p.estadio.id = :idEstadio)
        AND (:idGamemode IS NULL OR p.gamemode.id = :idGamemode)
        AND (:idTemporada IS NULL OR p.temporada.id = :idTemporada)
        GROUP BY j.id, j.userName
    """, nativeQuery = false)
    List<Object[]> getEstadisticasFiltradasRaw(
            @Param("idJugador") Long idJugador,
            @Param("idDecal") Long idDecal,
            @Param("idBoost") Long idBoost,
            @Param("idBody") Long idBody,
            @Param("idWheels") Long idWheels,
            @Param("idEstadio") Long idEstadio,
            @Param("idGamemode") Long idGamemode,
            @Param("idTemporada") Long idTemporada
    );

    @Query(value = """
        SELECT j.id, j.userName,
               COALESCE(SUM(jpc.goles), 0L),
               COALESCE(SUM(jpc.asistencias), 0L),
               COALESCE(SUM(jpc.salvadas), 0L),
               COALESCE(SUM(CASE WHEN jpc.esGanador = true THEN 1 ELSE 0 END), 0L),
               COALESCE(COUNT(DISTINCT p.id), 0L),
               COALESCE(SUM(jpc.tiros), 0L)
        FROM Jugador_x_Partido_x_Carro jpc
        JOIN jpc.jugador j
        JOIN jpc.partido p
        WHERE (:idGamemode IS NULL OR p.gamemode.id = :idGamemode)
        AND (:idPais IS NULL OR j.pais.id = :idPais)
        GROUP BY j.id, j.userName
    """, nativeQuery = false)
    List<Object[]> getLeaderboardRaw(
            @Param("idGamemode") Long idGamemode,
            @Param("idPais") Long idPais
    );

    @Query(value = """
        SELECT c.id, c.body.id, c.decal.id, c.wheels.id, c.boost.id,
               COALESCE(SUM(jpc.goles), 0L),
               COALESCE(SUM(CASE WHEN jpc.esGanador = true THEN 1 ELSE 0 END), 0L),
               COALESCE(COUNT(DISTINCT p.id), 0L),
               COALESCE(SUM(jpc.tiros), 0L)
        FROM Jugador_x_Partido_x_Carro jpc
        JOIN jpc.jugador j
        JOIN jpc.partido p
        JOIN jpc.carro c
        WHERE j.id = :idJugador
        AND (:idEstadio IS NULL OR p.estadio.id = :idEstadio)
        AND (:idGamemode IS NULL OR p.gamemode.id = :idGamemode)
        AND (:idTemporada IS NULL OR p.temporada.id = :idTemporada)
        AND (:idDecal IS NULL OR c.decal.id = :idDecal)
        AND (:idBoost IS NULL OR c.boost.id = :idBoost)
        AND (:idBody IS NULL OR c.body.id = :idBody)
        AND (:idWheels IS NULL OR c.wheels.id = :idWheels)
        GROUP BY c.id, c.body.id, c.decal.id, c.wheels.id, c.boost.id
    """, nativeQuery = false)
    List<Object[]> getCarPerformanceByJugadorRaw(
            @Param("idJugador") Long idJugador,
            @Param("idEstadio") Long idEstadio,
            @Param("idGamemode") Long idGamemode,
            @Param("idTemporada") Long idTemporada,
            @Param("idDecal") Long idDecal,
            @Param("idBoost") Long idBoost,
            @Param("idBody") Long idBody,
            @Param("idWheels") Long idWheels
    );

    @Query(value = """
        SELECT p.temporada.id, p.temporada.temporada,
               COALESCE(SUM(jpc.goles), 0L),
               COALESCE(SUM(CASE WHEN jpc.esGanador = true THEN 1 ELSE 0 END), 0L),
               COALESCE(COUNT(DISTINCT p.id), 0L),
               COALESCE(SUM(jpc.tiros), 0L)
        FROM Jugador_x_Partido_x_Carro jpc
        JOIN jpc.jugador j
        JOIN jpc.partido p
        WHERE j.id = :idJugador
        GROUP BY p.temporada.id, p.temporada.temporada
    """, nativeQuery = false)
    List<Object[]> getSeasonPerformanceByJugadorRaw(
            @Param("idJugador") Long idJugador
    );

}
