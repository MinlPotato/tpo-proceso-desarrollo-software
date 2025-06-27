package com.uade.tpo.application.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
public class Equipo {

    public Equipo() {
        // Constructor por defecto necesario para JPA
    }
    public Equipo(String nombre, Partido partido, List<Jugador> jugadores) {
        this.nombre = nombre;
        this.partido = partido;
        this.jugadores = jugadores;
        this.puntaje = 0;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "equipo_id", nullable = false)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partido_id", nullable = false)
    private Partido partido;

    // Cambio importante: ahora hay una lista de jugadores asociados
    @ManyToMany
    @JoinTable(
        name = "equipo_jugador",
        joinColumns = @JoinColumn(name = "equipo_id"),
        inverseJoinColumns = @JoinColumn(name = "jugador_id")
    )
    private List<Jugador> jugadores = new ArrayList<>();;

    @Column(name = "equipo_puntaje")
    private Integer puntaje;
}
