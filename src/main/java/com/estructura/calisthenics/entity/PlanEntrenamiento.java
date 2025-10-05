package com.estructura.calisthenics.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PlanEntrenamiento")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanEntrenamiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;
    private String descripcion;
    private String objetivo;

    public PlanEntrenamiento(String nombre, String descripcion, String objetivo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.objetivo = objetivo;
    }
}
