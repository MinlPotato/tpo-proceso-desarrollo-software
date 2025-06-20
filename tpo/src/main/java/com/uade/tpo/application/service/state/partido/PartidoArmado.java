package com.uade.tpo.application.service.state.partido;

import com.uade.tpo.application.entity.Partido;
public class PartidoArmado implements EstadoPartido {
    private final String nombre = "Armado";
    private final String descripcion = "Partido armado, esperando inscripciones";
    private final String mensaje = "El partido está armado y listo para inscripción.";

    @Override public String getNombre() { return nombre; }
    @Override public String getDescripcion() { return descripcion; }
    @Override public String getMensaje() { return mensaje; }
    @Override public EstadoPartido avanzar(Partido partido) { return new Confirmado(); }
    @Override public EstadoPartido cancelar(Partido partido) { return new Cancelado(); }
}