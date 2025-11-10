package com.estructura.rocketleague.service.impl;

import com.estructura.rocketleague.dto.CarPerformanceDTO;
import com.estructura.rocketleague.dto.JugadorEstadisticasDTO;
import com.estructura.rocketleague.dto.Jugador_x_Partido_x_CarroDTO;
import com.estructura.rocketleague.dto.LeaderboardEntryDTO;
import com.estructura.rocketleague.dto.SeasonPerformanceDTO;
import com.estructura.rocketleague.entity.Jugador_x_Partido_x_Carro;
import com.estructura.rocketleague.entity.Jugador;
import com.estructura.rocketleague.entity.Partido;
import com.estructura.rocketleague.entity.Carro;
import com.estructura.rocketleague.repository.Jugador_x_Partido_x_CarroRepository;
import com.estructura.rocketleague.repository.JugadorRepository;
import com.estructura.rocketleague.repository.Jugador_x_Rango_x_GamemodeRepository;
import com.estructura.rocketleague.service.Jugador_x_Partido_x_CarroService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Jugador_x_Partido_x_CarroServiceImpl implements Jugador_x_Partido_x_CarroService {

    private final Jugador_x_Partido_x_CarroRepository repository;
    private final JugadorRepository jugadorRepository;
    private final Jugador_x_Rango_x_GamemodeRepository rangoRepository;

    // Mínimo de partidos requeridos para considerar un carro en el ranking
    private static final long MIN_PARTIDOS = 3L;

    @Override
    public Jugador_x_Partido_x_Carro save(Jugador_x_Partido_x_Carro e) {
        return repository.save(e);
    }

    @Override
    public List<Jugador_x_Partido_x_Carro> listAll() {
        return repository.findAll();
    }

    @Override
    public Jugador_x_Partido_x_Carro update(Long id, Jugador_x_Partido_x_CarroDTO dto) {
        Jugador_x_Partido_x_Carro e = repository.findById(id).orElseThrow(() -> new RuntimeException("Not found: "+id));
        e.setGoles(dto.getGoles());
        e.setGolesRecibidos(dto.getGolesRecibidos());
        e.setAsistencias(dto.getAsistencias());
        e.setSalvadas(dto.getSalvadas());
        e.setEsGanador(dto.isEsGanador());
        e.setTiros(dto.getTiros());
        Jugador j = new Jugador(); j.setId(dto.getJugadorId());
        Partido p = new Partido(); p.setId(dto.getPartidoId());
        Carro c = new Carro(); c.setId(dto.getCarroId());
        e.setJugador(j);
        e.setPartido(p);
        e.setCarro(c);
        return repository.save(e);
    }

    @Override
    public List<JugadorEstadisticasDTO> getEstadisticasFiltradas(
            String identificador,
            Long idDecal,
            Long idBoost,
            Long idBody,
            Long idWheels,
            Long idEstadio,
            Long idGamemode,
            Long idTemporada
    ) {
        if (identificador == null || identificador.trim().isEmpty()) {
            throw new IllegalArgumentException("El identificador del jugador es obligatorio");
        }

        // Determinar si el identificador es un ID numérico o un username
        Long idJugador;
        String username;

        if (identificador.matches("\\d+")) {
            // Es un ID numérico
            idJugador = Long.parseLong(identificador);
            // Obtener el username del jugador
            Jugador jugador = jugadorRepository.findById(idJugador)
                    .orElseThrow(() -> new IllegalArgumentException("Jugador no encontrado con ID: " + idJugador));
            username = jugador.getUserName();
        } else {
            // Es un username
            Jugador jugador = jugadorRepository.findByUserName(identificador)
                    .orElseThrow(() -> new IllegalArgumentException("Jugador no encontrado con username: " + identificador));
            idJugador = jugador.getId();
            username = jugador.getUserName();
        }

        // Ahora ejecutamos la query con el ID del jugador
        List<Object[]> rows = repository.getEstadisticasFiltradasRaw(
                idJugador, idDecal, idBoost, idBody, idWheels, idEstadio, idGamemode, idTemporada
        );

        if (rows == null || rows.isEmpty()) {
            // si no hay registros que cumplan los filtros, devolver un DTO con ceros
            JugadorEstadisticasDTO empty = new JugadorEstadisticasDTO(idJugador, username, 0L, 0L, 0L, 0L, 0L, 0.0, 0L, 0.0);
            return Collections.singletonList(empty);
        }

        List<JugadorEstadisticasDTO> result = new ArrayList<>();
        for (Object[] row : rows) {
            Long jugadorId = row[0] == null ? idJugador : ((Number) row[0]).longValue();
            String userName = row[1] == null ? username : row[1].toString();
            Long goles = row[2] == null ? 0L : ((Number) row[2]).longValue();
            Long asistencias = row[3] == null ? 0L : ((Number) row[3]).longValue();
            Long salvadas = row[4] == null ? 0L : ((Number) row[4]).longValue();
            Long victorias = row[5] == null ? 0L : ((Number) row[5]).longValue();
            long partidos = row.length > 6 && row[6] != null ? ((Number) row[6]).longValue() : 0L;
            long tiros = row.length > 7 && row[7] != null ? ((Number) row[7]).longValue() : 0L;
            double winPct = partidos == 0 ? 0.0 : (victorias.doubleValue() * 100.0) / (double) partidos;
            double goalShotRatio = tiros == 0L ? 0.0 : goles.doubleValue() / (double) tiros;
            result.add(new JugadorEstadisticasDTO(jugadorId, userName, goles, asistencias, salvadas, victorias, partidos, winPct, tiros, goalShotRatio));
        }

        return result;
    }

    @Override
    public List<LeaderboardEntryDTO> getLeaderboard(
            Long idGamemode,
            Long idPais,
            Integer limit
    ) {
        // default gamemode = 5 if not provided
        Long gm = idGamemode == null ? 5L : idGamemode;

        List<Object[]> rows = rangoRepository.getLeaderboardByGamemodeRaw(gm, idPais);
        
        System.out.println("DEBUG: Rows retrieved from DB: " + (rows == null ? "null" : rows.size()));
        System.out.println("DEBUG: idGamemode=" + gm + ", idPais=" + idPais + ", limit=" + limit);
        
        if (rows == null || rows.isEmpty()) return Collections.emptyList();

        List<LeaderboardEntryDTO> entries = new ArrayList<>();
        for (Object[] row : rows) {
            Long jugadorId = row[0] == null ? null : ((Number) row[0]).longValue();
            String username = row[1] == null ? "UNKNOWN" : row[1].toString();
            Double puntuacion = row[2] == null ? 0.0 : ((Number) row[2]).doubleValue();

            // fill other numeric fields with zeros since we're ordering by puntuacion
            entries.add(new LeaderboardEntryDTO(jugadorId, username, 0L, 0L, 0L, 0L, 0L, 0L, puntuacion));
        }

        System.out.println("DEBUG: Entries created: " + entries.size());

        // rows already ordered by puntuacion desc in the query, but ensure it and apply limit
        List<LeaderboardEntryDTO> sorted = entries.stream()
                .sorted((LeaderboardEntryDTO a, LeaderboardEntryDTO b) -> Double.compare(
                        b.getScore() == null ? 0.0 : b.getScore(),
                        a.getScore() == null ? 0.0 : a.getScore()
                ))
                .collect(Collectors.toList());

        System.out.println("DEBUG: After sorting: " + sorted.size());
        
        if (limit != null && limit > 0 && sorted.size() > limit) {
            System.out.println("DEBUG: Applying limit, returning " + limit + " items");
            return sorted.subList(0, limit);
        }
        
        System.out.println("DEBUG: Returning all " + sorted.size() + " items");
        return sorted;
    }

    @Override
    public List<CarPerformanceDTO> getTopNCarsForJugador(
            Long idJugador,
            Integer limit
    ) {
        if (idJugador == null) throw new IllegalArgumentException("idJugador es obligatorio");

        // Llamamos al repo pasando nulls para los filtros removidos (estadios, gamemode, temporada, decals, etc.)
        List<Object[]> rows = repository.getCarPerformanceByJugadorRaw(
                idJugador, null, null, null, null, null, null, null
        );

        if (rows == null || rows.isEmpty()) return Collections.emptyList();

        List<CarPerformanceDTO> list = new ArrayList<>();
        for (Object[] row : rows) {
            Long carroId = row[0] == null ? null : ((Number) row[0]).longValue();
            Long bodyId = row[1] == null ? null : ((Number) row[1]).longValue();
            Long decalId = row[2] == null ? null : ((Number) row[2]).longValue();
            Long wheelsId = row[3] == null ? null : ((Number) row[3]).longValue();
            Long boostId = row[4] == null ? null : ((Number) row[4]).longValue();
            Long goles = row[5] == null ? 0L : ((Number) row[5]).longValue();
            Long victorias = row[6] == null ? 0L : ((Number) row[6]).longValue();
            Long partidos = row[7] == null ? 0L : ((Number) row[7]).longValue();
            Long tiros = row[8] == null ? 0L : ((Number) row[8]).longValue();

            double winPct = (partidos == 0L) ? 0.0 : (victorias.doubleValue() * 100.0) / (double) partidos;
            double goalShotRatio = (tiros == 0L) ? 0.0 : goles.doubleValue() / (double) tiros;

            // Score combinado: ponderamos win% más (70%) y goalShotRatio convertido a % (goalShotRatio*100) con 30%
            double score = winPct * 0.7 + (goalShotRatio * 100.0) * 0.3;

            CarPerformanceDTO dto = new CarPerformanceDTO(carroId, bodyId, decalId, wheelsId, boostId,
                    goles, victorias, partidos, tiros,
                    winPct, goalShotRatio, score);
            list.add(dto);
        }

        // Filtrar carros con menos de MIN_PARTIDOS partidos (mínimo requerido)
        List<CarPerformanceDTO> filtered = list.stream()
                .filter(d -> d.getPartidos() != null && d.getPartidos() >= MIN_PARTIDOS)
                .toList();

        if (filtered.isEmpty()) return Collections.emptyList();

        // Ordenar por score descendente
        List<CarPerformanceDTO> sorted = filtered.stream()
                .sorted(Comparator.comparingDouble(CarPerformanceDTO::getScore).reversed())
                .collect(Collectors.toList());

        if (limit != null && limit > 0 && sorted.size() > limit) {
            return sorted.subList(0, limit);
        }
        return sorted;
    }

    @Override
    public List<SeasonPerformanceDTO> getSeasonPerformanceByJugador(Long idJugador, Integer minMatches, Integer limit) {
        if (idJugador == null) throw new IllegalArgumentException("idJugador es obligatorio");

        int min = (minMatches == null || minMatches < 1) ? 1 : minMatches;

        List<Object[]> rows = repository.getSeasonPerformanceByJugadorRaw(idJugador);
        if (rows == null || rows.isEmpty()) return Collections.emptyList();

        List<SeasonPerformanceDTO> list = new ArrayList<>();
        for (Object[] row : rows) {
            Long temporadaId = row[0] == null ? null : ((Number) row[0]).longValue();
            String temporadaName = row[1] == null ? "UNKNOWN" : row[1].toString();
             long goles = row[2] == null ? 0L : ((Number) row[2]).longValue();
             long victorias = row[3] == null ? 0L : ((Number) row[3]).longValue();
             long partidos = row[4] == null ? 0L : ((Number) row[4]).longValue();
             long tiros = row[5] == null ? 0L : ((Number) row[5]).longValue();

             double winPct = (partidos == 0L) ? 0.0 : ((double) victorias * 100.0) / (double) partidos;
             double goalShotRatio = (tiros == 0L) ? 0.0 : ((double) goles) / (double) tiros;
             double score = winPct * 0.7 + (goalShotRatio * 100.0) * 0.3;

            SeasonPerformanceDTO dto = new SeasonPerformanceDTO(temporadaId, temporadaName, goles, victorias, partidos, tiros, winPct, goalShotRatio, score);
            list.add(dto);
        }

        // Filtrar por min matches
         List<SeasonPerformanceDTO> filtered = list.stream()
                 .filter(s -> s.getPartidos() != null && s.getPartidos() >= min)
                 .sorted(Comparator.comparingDouble(SeasonPerformanceDTO::getScore).reversed())
                 .collect(Collectors.toList());


        if (limit != null && limit > 0 && filtered.size() > limit) {
            return filtered.subList(0, limit);
        }
        return filtered;
    }
}
