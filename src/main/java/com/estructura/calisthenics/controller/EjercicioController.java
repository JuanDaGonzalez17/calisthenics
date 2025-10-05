package com.estructura.calisthenics.controller;

import java.util.List;

import com.estructura.calisthenics.dto.EjercicioDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.estructura.calisthenics.entity.Ejercicio;
import com.estructura.calisthenics.service.EjercicioService;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
@RestController
@RequestMapping("api/Ejercicio")
@RequiredArgsConstructor
public class EjercicioController {

    private final EjercicioService ejercicioService;

    @PostMapping("/save")
    public ResponseEntity<?>saveEjercicio(@RequestBody EjercicioDTO dto) {
        try{
            Ejercicio ejercicio = new Ejercicio(
                dto.getNombre(),
                dto.getDescripcion()
            );
//            Ejercicio saved = ejercicioService.saveEjercicio(ejercicio);
            return ResponseEntity.ok().body(ejercicioService.saveEjercicio(ejercicio));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/listAll")
    public ResponseEntity<List<Ejercicio>> listAllTasks() {
        return ResponseEntity.ok().body(ejercicioService.listAllEjercicios());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateEjercicio(@PathVariable Long id, @RequestBody EjercicioDTO ejerciciodto) {

        Ejercicio updatedEjercicio = ejercicioService.updateEjercicio(id, ejerciciodto);
        return ResponseEntity.ok(updatedEjercicio);
    }
}
