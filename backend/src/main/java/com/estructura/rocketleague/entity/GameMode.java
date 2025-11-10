package com.estructura.rocketleague.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "gamemode")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameMode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String gameMode;

    public GameMode(String gameMode) {
        this.gameMode = gameMode;
    }
}
