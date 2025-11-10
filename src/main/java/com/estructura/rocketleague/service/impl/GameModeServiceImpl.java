package com.estructura.rocketleague.service.impl;

import com.estructura.rocketleague.dto.GameModeDTO;
import com.estructura.rocketleague.entity.GameMode;
import com.estructura.rocketleague.repository.GameModeRepository;
import com.estructura.rocketleague.service.GameModeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameModeServiceImpl implements GameModeService {

    private final GameModeRepository gameModeRepository;

    @Override
    public GameMode saveGameMode(GameMode gameMode) {
        return gameModeRepository.save(gameMode);
    }

    @Override
    public List<GameMode> listAllGameModes() {
        return gameModeRepository.findAll();
    }

    @Override
    public GameMode updateGameMode(Long id, GameModeDTO dto) {
        GameMode g = gameModeRepository.findById(id).orElseThrow(() -> new RuntimeException("GameMode not found: "+id));
        g.setGameMode(dto.getGameMode());
        return gameModeRepository.save(g);
    }
}
