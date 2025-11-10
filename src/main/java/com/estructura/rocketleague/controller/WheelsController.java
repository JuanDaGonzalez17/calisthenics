
package com.estructura.rocketleague.controller;

import com.estructura.rocketleague.dto.WheelsDTO;
import com.estructura.rocketleague.entity.Wheels;
import com.estructura.rocketleague.service.WheelsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/wheels")
@RequiredArgsConstructor
public class WheelsController {

    private final WheelsService wheelsService;
    @PostMapping("/save")
    public ResponseEntity<?> saveWheels(@RequestBody WheelsDTO dto) {
        try{
            Wheels wheels = new Wheels(dto.getWheels());
            return ResponseEntity.ok().body(wheelsService.saveWheels(wheels));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/listAll")

    public ResponseEntity<List<Wheels>> listAll() {
        return ResponseEntity.ok().body(wheelsService.listAllWheels());
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateWheels(@PathVariable Long id, @RequestBody WheelsDTO dto) {
        Wheels updated = wheelsService.updateWheels(id, dto);
        return ResponseEntity.ok(updated);
    }
}

