package com.estructura.rocketleague.repository;

import com.estructura.rocketleague.entity.Rango_x_Gamemode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Rango_x_GamemodeRepository extends JpaRepository<Rango_x_Gamemode, Long> {
    List<Rango_x_Gamemode> findByRango_Id(Long rangoId);
    List<Rango_x_Gamemode> findByGamemode_Id(Long gamemodeId);
    List<Rango_x_Gamemode> findByRango_IdAndGamemode_Id(Long rangoId, Long gamemodeId);
}

