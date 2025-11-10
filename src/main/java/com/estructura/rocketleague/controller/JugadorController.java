package com.estructura.rocketleague.controller;

import com.estructura.rocketleague.dto.JugadorDTO;
import com.estructura.rocketleague.dto.LeaderboardEntryDTO;
import com.estructura.rocketleague.entity.Jugador;
import com.estructura.rocketleague.entity.Pais;
import com.estructura.rocketleague.service.JugadorService;
import com.estructura.rocketleague.service.Jugador_x_Partido_x_CarroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/Jugador")
@RequiredArgsConstructor
public class JugadorController {

    private final JugadorService jugadorService;
    private final Jugador_x_Partido_x_CarroService jpcService;

    @PostMapping("/save")
    public ResponseEntity<?> saveJugador(@RequestBody JugadorDTO dto) {
        try{
            Pais pais = null;
            if (dto.getPaisId() != null) {
                pais = new Pais();
                pais.setId(dto.getPaisId());
            }
            Jugador jugador = new Jugador(
                    dto.getNombre(),
                    dto.getEmail(),
                    dto.getTelefono(),
                    dto.getUserName(),
                    dto.getFechaNacimiento(),
                    pais
            );
            return ResponseEntity.ok().body(jugadorService.saveJugador(jugador));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/listAll")
    public ResponseEntity<List<Jugador>> listAll() {
        return ResponseEntity.ok().body(jugadorService.listAllJugadores());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateJugador(@PathVariable Long id, @RequestBody JugadorDTO jugadordto) {

        Jugador updatedJugador = jugadorService.updateJugador(id, jugadordto);
        return ResponseEntity.ok(updatedJugador);
    }

    // Nuevo endpoint: leaderboard con filtros permitidos: idGamemode y idPais
    @CrossOrigin(origins = "*")
    @GetMapping("/leaderboard")
    public ResponseEntity<List<LeaderboardEntryDTO>> leaderboard(
            @RequestParam(required = false) Long idGamemode,
            @RequestParam(required = false) Long idPais,
            @RequestParam(required = false, defaultValue = "10") Integer limit
    ) {
        List<LeaderboardEntryDTO> leaderboard = jpcService.getLeaderboard(
                idGamemode, idPais, limit
        );
        return ResponseEntity.ok(leaderboard);
    }
}
