package com.estructura.rocketleague.controller;

import com.estructura.rocketleague.dto.DecalDTO;
import com.estructura.rocketleague.entity.Decal;
import com.estructura.rocketleague.service.DecalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/decal")
@RequiredArgsConstructor
public class DecalController {

    private final DecalService decalService;

    @PostMapping("/save")
    public ResponseEntity<?> saveDecal(@RequestBody DecalDTO dto) {
        try{
            Decal d = new Decal(dto.getDecal());
            return ResponseEntity.ok().body(decalService.saveDecal(d));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/listAll")
    public ResponseEntity<List<Decal>> listAll() {
        return ResponseEntity.ok().body(decalService.listAllDecals());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateDecal(@PathVariable Long id, @RequestBody DecalDTO dto) {
        Decal updated = decalService.updateDecal(id, dto);
        return ResponseEntity.ok(updated);
    }
}
