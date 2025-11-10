package com.estructura.rocketleague.controller;

import com.estructura.rocketleague.dto.TemporadaDTO;
import com.estructura.rocketleague.entity.Temporada;
import com.estructura.rocketleague.service.TemporadaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/temporada")
@RequiredArgsConstructor
public class TemporadaController {

    private final TemporadaService temporadaService;

    @PostMapping("/save")
    public ResponseEntity<?> saveTemporada(@RequestBody TemporadaDTO dto) {
        try{
            Temporada t = new Temporada(dto.getTemporada(), dto.getFechaInicio(), dto.getFechaFin());
            return ResponseEntity.ok().body(temporadaService.saveTemporada(t));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/listAll")
    public ResponseEntity<List<Temporada>> listAll() {
        return ResponseEntity.ok().body(temporadaService.listAllTemporadas());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateTemporada(@PathVariable Long id, @RequestBody TemporadaDTO dto) {
        Temporada updated = temporadaService.updateTemporada(id, dto);
        return ResponseEntity.ok(updated);
    }
}
