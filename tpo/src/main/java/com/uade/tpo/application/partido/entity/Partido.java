package com.uade.tpo.application.partido.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.List;

import com.uade.tpo.application.deporte.entity.Deporte;
import com.uade.tpo.application.equipo.entity.Equipo;
import com.uade.tpo.application.estadoPartido.entity.EstadoPartido;
import com.uade.tpo.application.jugador.entity.Jugador;
import com.uade.tpo.application.partido.strategy.StrategyAdmisionPartido;
import com.uade.tpo.application.resultadoFinal.entity.ResultadoFinal;

@Getter
@Setter
@Entity
@Table(name = "partido")
public class Partido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "creador_id", nullable = false)
    private Jugador creador;

    @ManyToOne
    @JoinColumn(name = "deporte_id", nullable = false)
    private Deporte deporte;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "partido_id")
    private List<StrategyAdmisionPartido> admitidos;

    @Column(nullable = false)
    private Double duracion; // En horas

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date horario;

    @Column(nullable = false)
    private String ubicacion;

    @ManyToOne
    @JoinColumn(name = "estado_id", nullable = false)
    private EstadoPartido estado;

    @ManyToMany
    @JoinTable(
        name = "partido_equipo",
        joinColumns = @JoinColumn(name = "partido_id"),
        inverseJoinColumns = @JoinColumn(name = "equipo_id")
    )
    private List<Equipo> equipos;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "resultado_id", referencedColumnName = "id")
    private ResultadoFinal resultado;
}