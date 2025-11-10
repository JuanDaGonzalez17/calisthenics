package com.estructura.rocketleague.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "decal")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Decal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String decal;

    public Decal(String decal) {
        this.decal = decal;
    }
}
