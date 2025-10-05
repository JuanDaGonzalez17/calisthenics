package com.estructura.calisthenics.entity;

import java.time.LocalDate;
import java.util.ArrayList;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;
    private String email;
    private int telefono;
    private double peso;
    private double altura;
    private LocalDate fechanacimiento;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Nivel> nivel = new ArrayList<?>();

    public Usuario(String nombre, String email, int telefono, double peso, double altura, LocalDate fechanacimiento) {
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.peso = peso;
        this.altura = altura;
        this.fechanacimiento = fechanacimiento;
    }
}
