package com.estructura.rocketleague.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "wheels")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wheels {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String wheels;

    public Wheels(String wheels) {
        this.wheels = wheels;
    }
}
