package com.estructura.rocketleague.service;

import com.estructura.rocketleague.dto.GameModeDTO;
import com.estructura.rocketleague.entity.GameMode;

import java.util.List;

public interface GameModeService {
    GameMode saveGameMode(GameMode gameMode);
    List<GameMode> listAllGameModes();
    GameMode updateGameMode(Long id, GameModeDTO dto);
}
