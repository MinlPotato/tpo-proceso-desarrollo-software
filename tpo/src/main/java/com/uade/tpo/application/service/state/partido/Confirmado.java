package com.uade.tpo.application.service.state.partido;


import com.uade.tpo.application.entity.Partido;
public class Confirmado implements EstadoPartido {
    private final String nombre = "Confirmado";
    private final String descripcion = "Partido confirmado y listo para jugar";
    private final String mensaje = "El partido ha sido confirmado.";

    @Override public String getNombre() { return nombre; }
    @Override public String getDescripcion() { return descripcion; }
    @Override public String getMensaje() { return mensaje; }
    @Override public EstadoPartido avanzar(Partido partido) { return new EnJuego(); }
    @Override public EstadoPartido cancelar(Partido partido) { return new Cancelado(); }
}