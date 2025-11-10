package com.estructura.rocketleague.repository;

import com.estructura.rocketleague.entity.GameMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameModeRepository extends JpaRepository<GameMode, Long> {
}
