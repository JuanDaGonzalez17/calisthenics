package com.estructura.rocketleague.controller;

import com.estructura.rocketleague.dto.BodyDTO;
import com.estructura.rocketleague.entity.Body;
import com.estructura.rocketleague.service.BodyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/body")
@RequiredArgsConstructor
public class BodyController {

    private final BodyService bodyService;

    @PostMapping("/save")
    public ResponseEntity<?> saveBody(@RequestBody BodyDTO dto) {
        try{
            Body b = new Body(dto.getBody());
            return ResponseEntity.ok().body(bodyService.saveBody(b));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/listAll")
    public ResponseEntity<List<Body>> listAll() {
        return ResponseEntity.ok().body(bodyService.listAllBody());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateBody(@PathVariable Long id, @RequestBody BodyDTO dto) {
        Body updated = bodyService.updateBody(id, dto);
        return ResponseEntity.ok(updated);
    }
}
