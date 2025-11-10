package com.estructura.rocketleague.controller;

import com.estructura.rocketleague.dto.PaisDTO;
import com.estructura.rocketleague.entity.Pais;
import com.estructura.rocketleague.service.PaisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/pais")
@RequiredArgsConstructor
public class PaisController {

    private final PaisService paisService;
    @PostMapping("/save")
    public ResponseEntity<?> savePais(@RequestBody PaisDTO dto) {
        try{
            Pais p = new Pais(dto.getPais());
            return ResponseEntity.ok().body(paisService.savePais(p));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/listAll")
    public ResponseEntity<List<Pais>> listAll() {
        return ResponseEntity.ok().body(paisService.listAllPais());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePais(@PathVariable Long id, @RequestBody PaisDTO dto) {
        Pais updated = paisService.updatePais(id, dto);
        return ResponseEntity.ok(updated);
    }
}

