package com.estructura.rocketleague.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "carro")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Carro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "body_id", nullable = false)
    private Body body;

    @ManyToOne
    @JoinColumn(name = "decal_id", nullable = false)
    private Decal decal;

    @ManyToOne
    @JoinColumn(name = "wheels_id", nullable = false)
    private Wheels wheels;

    @ManyToOne
    @JoinColumn(name = "boost_id", nullable = false)
    private Boost boost;

    public Carro(Body body, Decal decal, Wheels wheels, Boost boost) {
        this.body = body;
        this.decal = decal;
        this.wheels = wheels;
        this.boost = boost;
    }

}
