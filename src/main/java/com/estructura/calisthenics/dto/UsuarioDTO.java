package com.estructura.calisthenics.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UsuarioDTO {

    private String nombre;
    private String email;
    private int telefono;
    private double peso;
    private double altura;
    private LocalDate fechanacimiento;
}
