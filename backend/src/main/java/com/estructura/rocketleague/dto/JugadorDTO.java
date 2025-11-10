package com.estructura.rocketleague.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JugadorDTO {
    private String nombre;
    private String email;
    private String telefono;
    private String userName;
    private LocalDate fechaNacimiento;
    private Long paisId;
}
