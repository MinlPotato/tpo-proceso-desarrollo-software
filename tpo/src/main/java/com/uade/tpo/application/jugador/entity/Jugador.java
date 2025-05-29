package com.uade.tpo.application.jugador.entity;

import java.util.ArrayList;
import java.util.List;

import com.uade.tpo.application.deporte.entity.Deporte;
import com.uade.tpo.application.equipo.entity.Equipo;
import com.uade.tpo.application.nivel.entity.Nivel;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Jugador {

    public Jugador() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "jugador_id", nullable = false)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "ubicacion", nullable = false)
    private String ubicacion;

    @OneToMany(mappedBy = "jugador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Nivel> niveles = new ArrayList<>();

@ManyToMany
@JoinTable(name = "jugador_deporte",
    joinColumns = @JoinColumn(name = "jugador_id"),
    inverseJoinColumns = @JoinColumn(name = "deporte_id"))
private List<Deporte> deportes;

    @ManyToOne
    @JoinColumn(name = "equipo_id", nullable = false)
    private Equipo equipo;

    // @OneToMany(mappedBy = "jugador")
}
