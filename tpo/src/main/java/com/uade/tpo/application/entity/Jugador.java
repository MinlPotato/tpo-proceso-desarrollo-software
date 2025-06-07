package com.uade.tpo.application.entity;

import java.util.ArrayList;
import java.util.List;

import com.uade.tpo.application.dto.JugadorCreateDTO;
import com.uade.tpo.application.dto.JugadorDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Jugador {

    public Jugador() {
    }

    public Jugador(JugadorCreateDTO jugadorCreateDTO) {
        this.nombre = jugadorCreateDTO.getNombre();
        this.email = jugadorCreateDTO.getEmail();
        this.ubicacion = jugadorCreateDTO.getUbicacion();
    }

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


}
