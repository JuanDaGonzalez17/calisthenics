package com.estructura.rocketleague.controller;

import com.estructura.rocketleague.dto.EstadioDTO;
import com.estructura.rocketleague.entity.Estadio;
import com.estructura.rocketleague.service.EstadioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/estadio")
@RequiredArgsConstructor
public class EstadioController {

    private final EstadioService estadioService;

    @PostMapping("/save")
    public ResponseEntity<?> saveEstadio(@RequestBody EstadioDTO dto) {
        try{
            Estadio e = new Estadio(dto.getEstadio());
            return ResponseEntity.ok().body(estadioService.saveEstadio(e));
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/listAll")
    public ResponseEntity<List<Estadio>> listAll() {
        return ResponseEntity.ok().body(estadioService.listAllEstadios());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateEstadio(@PathVariable Long id, @RequestBody EstadioDTO dto) {
        Estadio updated = estadioService.updateEstadio(id, dto);
        return ResponseEntity.ok(updated);
    }
}
