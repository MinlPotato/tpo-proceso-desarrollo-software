package com.uade.tpo.application.entity;

import com.uade.tpo.application.enums.NivelDeporte;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "nivel")
@Getter
@Setter
public class Nivel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nivel_id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "deporte_id", nullable = false)
    private Deporte deporte;

    @ManyToOne
    @JoinColumn(name = "jugador_id", nullable = false)
    private Jugador jugador;

    @Column(name = "nivel", nullable = false)
    @Enumerated(EnumType.STRING)
    private NivelDeporte nivel;

    @Column(name = "favorito", nullable = false)
    private Boolean favorito;

    public Nivel() {}

    public Nivel(Deporte deporte, Jugador jugador, NivelDeporte nivel, Boolean favorito) {
        this.deporte = deporte;
        this.jugador = jugador;
        this.nivel = nivel;
        this.favorito = favorito;
    }
}