package com.uade.tpo.application.service.state.partido;
import com.uade.tpo.application.entity.Partido;

public class NecesitaJugadores implements EstadoPartido {
    private final String nombre = "Necesita Jugadores";
    private final String descripcion = "Faltan jugadores para completar equipos";
    private final String mensaje = "Esperando más jugadores.";

    @Override public String getNombre() { return nombre; }
    @Override public String getDescripcion() { return descripcion; }
    @Override public String getMensaje() { return mensaje; }
    @Override public EstadoPartido avanzar(Partido partido) { return new Confirmado(); }
    @Override public EstadoPartido cancelar(Partido partido) { return new Cancelado(); }
}
