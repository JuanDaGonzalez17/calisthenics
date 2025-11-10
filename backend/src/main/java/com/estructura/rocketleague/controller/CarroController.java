package com.estructura.rocketleague.controller;
import com.estructura.rocketleague.dto.CarroDTO;
import com.estructura.rocketleague.entity.Carro;
import com.estructura.rocketleague.entity.Body;
import com.estructura.rocketleague.entity.Decal;
import com.estructura.rocketleague.entity.Wheels;
import com.estructura.rocketleague.entity.Boost;
import com.estructura.rocketleague.service.CarroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/carro")
@RequiredArgsConstructor
public class CarroController {

    private final CarroService carroService;

    @PostMapping("/save")
    public ResponseEntity<?> saveCarro(@RequestBody CarroDTO dto) {
        try{
            Body body = new Body(); body.setId(dto.getBodyId());
            Decal decal = new Decal(); decal.setId(dto.getDecalId());
            Wheels wheels = new Wheels(); wheels.setId(dto.getWheelsId());
            Boost boost = new Boost(); boost.setId(dto.getBoostId());
            Carro c = new Carro(body, decal, wheels, boost);
            return ResponseEntity.ok().body(carroService.saveCarro(c));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/listAll")
    public ResponseEntity<List<Carro>> listAll() {
        return ResponseEntity.ok().body(carroService.listAllCarros());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCarro(@PathVariable Long id, @RequestBody CarroDTO dto) {
        Carro updated = carroService.updateCarro(id, dto);
        return ResponseEntity.ok(updated);
    }
}

