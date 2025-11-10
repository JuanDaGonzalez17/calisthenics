package com.estructura.rocketleague.controller;

import com.estructura.rocketleague.dto.Jugador_x_Rango_x_GamemodeDTO;
import com.estructura.rocketleague.entity.Jugador_x_Rango_x_Gamemode;
import com.estructura.rocketleague.entity.Jugador;
import com.estructura.rocketleague.entity.Rango;
import com.estructura.rocketleague.entity.GameMode;
import com.estructura.rocketleague.service.Jugador_x_Rango_x_GamemodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/jugador_x_rango_x_gamemode")
@RequiredArgsConstructor
public class Jugador_x_Rango_x_GamemodeController {

    private final Jugador_x_Rango_x_GamemodeService service;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Jugador_x_Rango_x_GamemodeDTO dto) {
        try{
            Jugador j = new Jugador(); j.setId(dto.getJugadorId());
            Rango r = new Rango(); r.setId(dto.getRangoId());
            GameMode g = new GameMode(); g.setId(dto.getGamemodeId());
            Jugador_x_Rango_x_Gamemode e = new Jugador_x_Rango_x_Gamemode(j, r, g, dto.getPuntuacion());
            return ResponseEntity.ok().body(service.save(e));
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/listAll")
    public ResponseEntity<List<Jugador_x_Rango_x_Gamemode>> listAll() {
        return ResponseEntity.ok().body(service.listAll());
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/byJugador")
    public ResponseEntity<List<Jugador_x_Rango_x_Gamemode>> listByJugador(@RequestParam Long idJugador) {
        if (idJugador == null) {
            return ResponseEntity.badRequest().build();
        }
        List<Jugador_x_Rango_x_Gamemode> result = service.listByJugadorId(idJugador);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Jugador_x_Rango_x_GamemodeDTO dto) {
        Jugador_x_Rango_x_Gamemode updated = service.update(id, dto);
        return ResponseEntity.ok(updated);
    }
}
