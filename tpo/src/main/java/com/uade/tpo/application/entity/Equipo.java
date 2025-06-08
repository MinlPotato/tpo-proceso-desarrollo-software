package com.uade.tpo.application.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
@Entity
public class Equipo {

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
    private List<Jugador> jugadores;
    public Equipo() {
        // Constructor por defecto necesario para JPA
    }
    public Equipo(String nombre, Partido partido, List<Jugador> jugadores) {
        this.nombre = nombre;
        this.partido = partido;
        this.jugadores = jugadores;
    }
    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = jugadores;
    }
    public List<Jugador> getJugadores() {
        return jugadores;
    }
    public void setPartido(Partido partido) {
        this.partido = partido;
    }
    public Partido getPartido() {
        return partido;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public boolean unirse(Jugador jugador) {
        if (jugadores == null) {
            jugadores = new java.util.ArrayList<>();
        }
        if (!jugadores.contains(jugador)) {
            jugadores.add(jugador);
            return true;
        }
        return false;
    }
    public boolean abandonar(Jugador jugador) {
        if (jugadores != null && jugadores.contains(jugador)) {
            jugadores.remove(jugador);
            return true;
        }
        return false;
    }
}
