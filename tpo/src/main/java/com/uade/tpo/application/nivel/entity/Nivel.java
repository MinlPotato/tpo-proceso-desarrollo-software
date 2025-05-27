package com.uade.tpo.application.nivel.entity;

import com.uade.tpo.application.deporte.entity.Deporte;
import com.uade.tpo.application.deporte.enums.NivelDeporte;
import com.uade.tpo.application.jugador.entity.Jugador;

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
