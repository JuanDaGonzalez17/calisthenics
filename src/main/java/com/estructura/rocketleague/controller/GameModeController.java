package com.estructura.rocketleague.controller;

import com.estructura.rocketleague.dto.GameModeDTO;
import com.estructura.rocketleague.entity.GameMode;
import com.estructura.rocketleague.service.GameModeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/gamemode")
@RequiredArgsConstructor
public class GameModeController {

    private final GameModeService gameModeService;

    @PostMapping("/save")
    public ResponseEntity<?> saveGameMode(@RequestBody GameModeDTO dto) {
        try{
            GameMode g = new GameMode(dto.getGameMode());
            return ResponseEntity.ok().body(gameModeService.saveGameMode(g));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/listAll")
    public ResponseEntity<List<GameMode>> listAll() {
        return ResponseEntity.ok().body(gameModeService.listAllGameModes());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateGameMode(@PathVariable Long id, @RequestBody GameModeDTO dto) {
        GameMode updated = gameModeService.updateGameMode(id, dto);
        return ResponseEntity.ok(updated);
    }
}

