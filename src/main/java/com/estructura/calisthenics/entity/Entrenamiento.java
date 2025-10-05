package com.estructura.calisthenics.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;

@Entity
@Table(name = "Entrenamiento")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Entrenamiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double caloriasTotales;
    private int dia;

    public Entrenamiento(int dia, double caloriasTotales) {
        this.dia = dia;
        this.caloriasTotales = caloriasTotales;
    }
}
