package com.uade.tpo.application.service.state.partido;

import com.uade.tpo.application.entity.Partido;

public class EnJuego implements EstadoPartido {
    private final String nombre = "En Juego";
    private final String descripcion = "Partido en curso";
    private final String mensaje = "El partido está en juego.";

    @Override public String getNombre() { return nombre; }
    @Override public String getDescripcion() { return descripcion; }
    @Override public String getMensaje() { return mensaje; }
    @Override public EstadoPartido avanzar(Partido partido) { return new Finalizado(); }
    @Override public EstadoPartido cancelar(Partido partido) { return new Cancelado(); }
}