package com.estructura.rocketleague.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity@Table(name = "body")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Body {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String body;

    public Body(String body) {
        this.body = body;
    }
}
