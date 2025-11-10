package com.estructura.rocketleague.controller;

import com.estructura.rocketleague.dto.PartidoDTO;
import com.estructura.rocketleague.entity.Partido;
import com.estructura.rocketleague.entity.GameMode;
import com.estructura.rocketleague.entity.Estadio;
import com.estructura.rocketleague.entity.Temporada;
import com.estructura.rocketleague.service.PartidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/partido")
@RequiredArgsConstructor
public class PartidoController {

    private final PartidoService partidoService;

    @PostMapping("/save")
    public ResponseEntity<?> savePartido(@RequestBody PartidoDTO dto) {
        try{
            GameMode gm = new GameMode(); gm.setId(dto.getGamemodeId());
            Estadio est = new Estadio(); est.setId(dto.getEstadioId());
            Temporada temp = new Temporada(); temp.setId(dto.getTemporadaId());
            Partido p = new Partido(dto.getFechaPartido(), dto.getDuracion(), gm, est, temp);
            return ResponseEntity.ok().body(partidoService.savePartido(p));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/listAll")
    public ResponseEntity<List<Partido>> listAll() {
        return ResponseEntity.ok().body(partidoService.listAllPartidos());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePartido(@PathVariable Long id, @RequestBody PartidoDTO dto) {
        Partido updated = partidoService.updatePartido(id, dto);
        return ResponseEntity.ok(updated);
    }
}
