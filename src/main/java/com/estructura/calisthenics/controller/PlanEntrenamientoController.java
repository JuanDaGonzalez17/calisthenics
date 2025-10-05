package com.estructura.calisthenics.controller;

import com.estructura.calisthenics.dto.PlanEntrenamientoDTO;
import com.estructura.calisthenics.dto.UsuarioDTO;
import com.estructura.calisthenics.entity.PlanEntrenamiento;
import com.estructura.calisthenics.entity.Usuario;
import com.estructura.calisthenics.service.PlanEntrenamientoService;
import com.estructura.calisthenics.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/PlanEntrenamiento")
@RequiredArgsConstructor
public class PlanEntrenamientoController {

    private final PlanEntrenamientoService planEntrenamientoService;

    @PostMapping("/save")
    public ResponseEntity<?>savePlanEntrenamiento(@RequestBody PlanEntrenamientoDTO dto) {
        try{
            PlanEntrenamiento planentrenamiento = new PlanEntrenamiento(
                    dto.getNombre(),
                    dto.getDescripcion(),
                    dto.getObjetivo()
            );
            return ResponseEntity.ok().body(planEntrenamientoService.savePlanEntrenamiento(planentrenamiento));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/listAll")
    public ResponseEntity<List<PlanEntrenamiento>> listAllTasks() {
        return ResponseEntity.ok().body(planEntrenamientoService.listAllPlanEntrenamiento());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePlanEntrenamiento(@PathVariable Long id, @RequestBody PlanEntrenamientoDTO planEntrenamientoDTO) {

        PlanEntrenamiento updatedPlanEntrenamiento = planEntrenamientoService.updatePlanEntrenamiento(id, planEntrenamientoDTO);
        return ResponseEntity.ok(updatedPlanEntrenamiento);
    }
}
