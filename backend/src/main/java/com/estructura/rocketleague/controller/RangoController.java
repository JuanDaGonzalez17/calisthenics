package com.estructura.rocketleague.controller;

import com.estructura.rocketleague.dto.RangoDTO;
import com.estructura.rocketleague.entity.Rango;
import com.estructura.rocketleague.service.RangoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/rango")
@RequiredArgsConstructor
public class RangoController {

    private final RangoService rangoService;

    @PostMapping("/save")
    public ResponseEntity<?> saveRango(@RequestBody RangoDTO dto) {
        try{
            Rango r = new Rango(dto.getRango(), dto.getDivision());
            return ResponseEntity.ok().body(rangoService.saveRango(r));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/listAll")
    public ResponseEntity<List<Rango>> listAll() {
        return ResponseEntity.ok().body(rangoService.listAllRangos());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateRango(@PathVariable Long id, @RequestBody RangoDTO dto) {
        Rango updated = rangoService.updateRango(id, dto);
        return ResponseEntity.ok(updated);
    }
}
