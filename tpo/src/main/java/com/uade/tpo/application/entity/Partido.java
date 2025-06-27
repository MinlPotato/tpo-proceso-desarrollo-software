package com.uade.tpo.application.entity;

import com.uade.tpo.application.dto.PartidoCreateDTO;
import com.uade.tpo.application.enums.EnumEstadoPartido;
import com.uade.tpo.application.enums.NivelDeporte;
import com.uade.tpo.application.service.state.partido.EstadoPartido;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "partido")
public class Partido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "creador_id", nullable = false)
    private Jugador creador;

    @ManyToOne
    @JoinColumn(name = "deporte_id", nullable = false)
    private Deporte deporte;

    @Column(nullable = false)
    private Double duracion; // TODO: Cambiar tipo de valor a LocalDateTime

    @Column(nullable = false)
    private LocalDateTime horario;

    @Column(nullable = false)
    private String ubicacion;

    @Column(name = "estado_partido", nullable = false)
    @Enumerated(EnumType.STRING)
    private EnumEstadoPartido estado;

    @Column(name = "cantidad_equipos", nullable = false)
    private Integer cantidadEquipos;
    
    @Column(name = "cantidad_jugadores_por_equipo", nullable = false)
    private Integer cantidadJugadoresPorEquipo;

    @ManyToMany
    @JoinTable(
            name = "partido_equipo",
            joinColumns = @JoinColumn(name = "partido_id"),
            inverseJoinColumns = @JoinColumn(name = "equipo_id")
    )
    private List<Equipo> equipos;

    @ElementCollection(targetClass = NivelDeporte.class)
    @CollectionTable(
        name = "partido_niveles",
        joinColumns = @JoinColumn(name = "partido_id")
    )
    @Column(name = "nivel")
    @Enumerated(EnumType.STRING)
    private List<NivelDeporte> nivelesJugadores;

    public Partido() {
        // Constructor por defecto
    }

    public Partido(PartidoCreateDTO partidoCreateDTO, Jugador jugador, Deporte deporte) {
        this.creador = jugador;
        this.deporte = deporte;
        this.duracion = partidoCreateDTO.getDuracion(); // TODO: Cambiar logica de agregar duracion
        this.horario = partidoCreateDTO.getHorario(); // TODO: Cambiar logica de agregar horario
        this.ubicacion = partidoCreateDTO.getUbicacion();
        this.estado = EnumEstadoPartido.NECESITA_JUGADORES;
        this.cantidadJugadoresPorEquipo = partidoCreateDTO.getCantidadJugadoresPorEquipo();
        this.cantidadEquipos = partidoCreateDTO.getCantidadEquipos();
        this.nivelesJugadores = partidoCreateDTO.getNivelesJugadores();
        this.equipos = new ArrayList<>();
    }


}