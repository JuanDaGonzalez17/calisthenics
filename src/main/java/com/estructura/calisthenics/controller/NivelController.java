package com.estructura.calisthenics.controller;

import com.estructura.calisthenics.dto.EjercicioDTO;
import com.estructura.calisthenics.dto.NivelDTO;
import com.estructura.calisthenics.entity.Ejercicio;
import com.estructura.calisthenics.entity.Nivel;
import com.estructura.calisthenics.service.EjercicioService;
import com.estructura.calisthenics.service.NivelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/Nivel")
@RequiredArgsConstructor
public class NivelController {

    private final NivelService nivelService;

    @PostMapping("/save")
    public ResponseEntity<?>saveNivel(@RequestBody NivelDTO dto) {
        try{
            Nivel nivel = new Nivel(
                dto.getNombre(),
                dto.getDescripcion()
            );
            return ResponseEntity.ok().body(nivelService.saveNivel(nivel));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/listAll")
    public ResponseEntity<List<Nivel>> listAllTasks() {
        return ResponseEntity.ok().body(nivelService.listAllNivel());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateNivel(@PathVariable Long id, @RequestBody NivelDTO niveldto) {

        Nivel updatedNivel = nivelService.updateNivel(id, niveldto);
        return ResponseEntity.ok(updatedNivel);
    }
}
