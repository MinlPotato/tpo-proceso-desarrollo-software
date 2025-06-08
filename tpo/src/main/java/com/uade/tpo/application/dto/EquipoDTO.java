package com.uade.tpo.application.dto;

import java.util.List;

public class EquipoDTO {
    private Long id;
    private String nombre;
    private Long idPartido;
    private List<Long> jugadores;

    public EquipoDTO(Long id, String nombre, Long idPartido, List<Long> jugadores) {
        this.id = id;
        this.nombre = nombre;
        this.idPartido = idPartido;
        this.jugadores = jugadores;
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

    public Long getIDPartido() {
        return idPartido;
    }
    public void setIdPartido(Long idPartido) {
        this.idPartido = idPartido;
    }

    public void setJugadores(List<Long> jugadores) {
        this.jugadores = jugadores;
    }

    public List<Long> getJugadoresIds() {
        return jugadores;
    }


}
