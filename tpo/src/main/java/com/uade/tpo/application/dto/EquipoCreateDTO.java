package com.uade.tpo.application.dto;

import java.util.List;

public class EquipoCreateDTO {
    private String nombre;
    private Long idPartido;
    private List<Long> jugadores;

    public EquipoCreateDTO(String nombre, Long idPartido, List<Long> jugadores) {
        this.nombre = nombre;
        this.idPartido = idPartido;
        this.jugadores = jugadores;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getIdPartido() {
        return idPartido;
    }

    public void setIdPartido(Long idPartido) {
        this.idPartido = idPartido;
    }

    public List<Long> getJugadoresIds() {
        return jugadores;
    }

    public void setJugadores(List<Long> jugadores) {
        this.jugadores = jugadores;
    }
}