package com.uade.tpo.application.service.state.partido;
import com.uade.tpo.application.entity.Partido;

public class Cancelado implements EstadoPartido {
    private final String nombre = "Cancelado";
    private final String descripcion = "Partido cancelado";
    private final String mensaje = "El partido ha sido cancelado.";

    @Override public String getNombre() { return nombre; }
    @Override public String getDescripcion() { return descripcion; }
    @Override public String getMensaje() { return mensaje; }
    @Override public EstadoPartido avanzar(Partido partido) { return this; }
    @Override public EstadoPartido cancelar(Partido partido) { return this; }
}
