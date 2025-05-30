package com.uade.tpo.application.entity;

import com.uade.tpo.application.service.state.partido.EstadoPartido;
import com.uade.tpo.application.service.strategy.partido.StrategyAdmisionPartido;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

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

    @Transient
    private List<StrategyAdmisionPartido> admitidos;

    @Column(nullable = false)
    private Double duracion; // En horas

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date horario;

    @Column(nullable = false)
    private String ubicacion;

    @Transient
    private EstadoPartido estado;

    @ManyToMany
    @JoinTable(
            name = "partido_equipo",
            joinColumns = @JoinColumn(name = "partido_id"),
            inverseJoinColumns = @JoinColumn(name = "equipo_id")
    )
    private List<Equipo> equipos;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "resultado_id", referencedColumnName = "resultado_final_id")
    private ResultadoFinal resultado;

}