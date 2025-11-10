package com.estructura.rocketleague.controller;

import com.estructura.rocketleague.dto.BoostDTO;
import com.estructura.rocketleague.entity.Boost;
import com.estructura.rocketleague.service.BoostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/boost")
@RequiredArgsConstructor
public class BoostController {

    private final BoostService boostService;

    @PostMapping("/save")
    public ResponseEntity<?> saveBoost(@RequestBody BoostDTO dto) {
        try{
            Boost b = new Boost(dto.getBoost());
            return ResponseEntity.ok().body(boostService.saveBoost(b));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/listAll")
    public ResponseEntity<List<Boost>> listAll() {
        return ResponseEntity.ok().body(boostService.listAllBoost());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateBoost(@PathVariable Long id, @RequestBody BoostDTO dto) {
        Boost updated = boostService.updateBoost(id, dto);
        return ResponseEntity.ok(updated);
    }
}
