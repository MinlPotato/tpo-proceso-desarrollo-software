package com.uade.tpo.application.entity;

import com.uade.tpo.application.enums.NivelDeporte;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "nivel")

public class Nivel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nivel_id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "deporte_id", nullable = false)
    private Deporte deporte;

    @OneToMany(mappedBy = "nivel")
    private Jugador jugador;

    @Column(name = "nivel", nullable = false)
    private NivelDeporte nivel;

    @Column(name = "favorito", nullable = false)
    private Boolean favorito;




}
