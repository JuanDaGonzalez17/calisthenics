package com.estructura.calisthenics.controller;

import com.estructura.calisthenics.dto.EntrenamientoDTO;
import com.estructura.calisthenics.entity.Entrenamiento;
import com.estructura.calisthenics.service.EntrenamientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/Entrenamiento")
@RequiredArgsConstructor
public class EstadoController {

    private final EntrenamientoService entrenamientoService;

    @PostMapping("/save")
    public ResponseEntity<?>saveEntrenamiento(@RequestBody EntrenamientoDTO dto) {
        try{
            Entrenamiento entrenamiento = new Entrenamiento(
                    dto.getDia(),
                    dto.getCaloriasTotales()
            );
            return ResponseEntity.ok().body(entrenamientoService.saveEntrenamiento(entrenamiento));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/listAll")
    public ResponseEntity<List<Entrenamiento>> listAllTasks() {
        return ResponseEntity.ok().body(entrenamientoService.listAllEntrenamiento());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateEntrenamiento(@PathVariable Long id, @RequestBody EntrenamientoDTO entrenamientodto) {

        Entrenamiento updatedEntrenamiento = entrenamientoService.updateEntrenamiento(id, entrenamientodto);
        return ResponseEntity.ok(updatedEntrenamiento);
    }
}
