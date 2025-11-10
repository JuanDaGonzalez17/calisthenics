package com.estructura.rocketleague.controller;

import com.estructura.rocketleague.dto.CarPerformanceDTO;
import com.estructura.rocketleague.dto.SeasonPerformanceDTO;
import com.estructura.rocketleague.dto.JugadorEstadisticasDTO;
import com.estructura.rocketleague.dto.Jugador_x_Partido_x_CarroDTO;
import com.estructura.rocketleague.dto.LeaderboardEntryDTO;
import com.estructura.rocketleague.entity.Jugador_x_Partido_x_Carro;
import com.estructura.rocketleague.entity.Jugador;
import com.estructura.rocketleague.entity.Partido;
import com.estructura.rocketleague.entity.Carro;
import com.estructura.rocketleague.service.Jugador_x_Partido_x_CarroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/jugador_x_partido_x_carro")
@RequiredArgsConstructor
public class Jugador_x_Partido_x_CarroController {

    private final Jugador_x_Partido_x_CarroService service;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Jugador_x_Partido_x_CarroDTO dto) {
        try{
            Jugador j = new Jugador(); j.setId(dto.getJugadorId());
            Partido p = new Partido(); p.setId(dto.getPartidoId());
            Carro c = new Carro(); c.setId(dto.getCarroId());
            Jugador_x_Partido_x_Carro e = new Jugador_x_Partido_x_Carro(dto.getGoles(), dto.getGolesRecibidos(), dto.getAsistencias(), dto.getSalvadas(), dto.isEsGanador(), j, p, c, dto.getTiros());
            return ResponseEntity.ok().body(service.save(e));
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/listAll")
    public ResponseEntity<List<Jugador_x_Partido_x_Carro>> listAll() {
        return ResponseEntity.ok().body(service.listAll());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Jugador_x_Partido_x_CarroDTO dto) {
        Jugador_x_Partido_x_Carro updated = service.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/estadisticas")
    public ResponseEntity<List<JugadorEstadisticasDTO>> estadisticas(
            @RequestParam Long idJugador,
            @RequestParam(required = false) Long idDecal,
            @RequestParam(required = false) Long idBoost,
            @RequestParam(required = false) Long idBody,
            @RequestParam(required = false) Long idWheels,
            @RequestParam(required = false) Long idEstadio,
            @RequestParam(required = false) Long idGamemode,
            @RequestParam(required = false) Long idTemporada
    ) {
        if (idJugador == null) {
            return ResponseEntity.badRequest().build();
        }

        List<JugadorEstadisticasDTO> result = service.getEstadisticasFiltradas(
                idJugador, idDecal, idBoost, idBody, idWheels, idEstadio, idGamemode, idTemporada
        );
        return ResponseEntity.ok(result);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/top-cars")
    public ResponseEntity<List<CarPerformanceDTO>> topCars(
            @RequestParam Long idJugador,
            @RequestParam(required = false) Integer limit
    ) {
        if (idJugador == null) {
            return ResponseEntity.badRequest().build();
        }
        List<CarPerformanceDTO> result = service.getTopNCarsForJugador(idJugador, limit);
        return ResponseEntity.ok(result);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/top-seasons")
    public ResponseEntity<List<SeasonPerformanceDTO>> topSeasons(
            @RequestParam Long idJugador,
            @RequestParam(required = false) Integer minMatches,
            @RequestParam(required = false) Integer limit
    ) {
        if (idJugador == null) {
            return ResponseEntity.badRequest().build();
        }
        List<SeasonPerformanceDTO> result = service.getSeasonPerformanceByJugador(idJugador, minMatches, limit);
        return ResponseEntity.ok(result);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/leaderboard")
    public ResponseEntity<List<LeaderboardEntryDTO>> leaderboard(
            @RequestParam(required = false, defaultValue = "5") Long idGamemode,
            @RequestParam(required = false) Long idPais,
            @RequestParam(required = false, defaultValue = "50") Integer limit
    ) {
        List<LeaderboardEntryDTO> result = service.getLeaderboard(idGamemode, idPais, limit);
        return ResponseEntity.ok(result);
    }
}
