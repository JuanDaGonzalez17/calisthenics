package com.estructura.rocketleague.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rango")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Rango {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String rango;
    private int division;

    public Rango(String rango, int division) {
        this.rango = rango;
        this.division = division;
    }
}
