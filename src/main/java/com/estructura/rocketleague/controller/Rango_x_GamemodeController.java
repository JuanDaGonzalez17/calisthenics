package com.estructura.rocketleague.controller;

import com.estructura.rocketleague.dto.Rango_x_GamemodeDTO;
import com.estructura.rocketleague.entity.Rango_x_Gamemode;
import com.estructura.rocketleague.entity.Rango;
import com.estructura.rocketleague.entity.GameMode;
import com.estructura.rocketleague.service.Rango_x_GamemodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/rango_x_gamemode")
@RequiredArgsConstructor
public class Rango_x_GamemodeController {

    private final Rango_x_GamemodeService service;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Rango_x_GamemodeDTO dto) {
        try {
            Rango r = new Rango(); r.setId(dto.getRangoId());
            GameMode g = new GameMode(); g.setId(dto.getGamemodeId());
            Rango_x_Gamemode e = new Rango_x_Gamemode(r, g, dto.getPuntuacionInicial(), dto.getPuntuacionFinal());
            return ResponseEntity.ok().body(service.save(e));
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/listAll")
    public ResponseEntity<List<Rango_x_Gamemode>> listAll() {
        return ResponseEntity.ok().body(service.listAll());
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/byRango")
    public ResponseEntity<List<Rango_x_Gamemode>> listByRango(@RequestParam Long rangoId) {
        if (rangoId == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(service.listByRangoId(rangoId));
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/byGamemode")
    public ResponseEntity<List<Rango_x_Gamemode>> listByGamemode(@RequestParam Long gamemodeId) {
        if (gamemodeId == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(service.listByGamemodeId(gamemodeId));
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/byRangoAndGamemode")
    public ResponseEntity<List<Rango_x_Gamemode>> listByRangoAndGamemode(@RequestParam Long rangoId, @RequestParam Long gamemodeId) {
        if (rangoId == null || gamemodeId == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(service.listByRangoAndGamemode(rangoId, gamemodeId));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Rango_x_GamemodeDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }
}

