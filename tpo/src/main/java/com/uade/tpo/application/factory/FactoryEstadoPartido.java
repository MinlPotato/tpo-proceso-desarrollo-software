package com.uade.tpo.application.factory;


import com.uade.tpo.application.enums.EnumEstadoPartido;
import com.uade.tpo.application.service.state.partido.*;
import org.springframework.stereotype.Component;

/**
 * Crea instancias de EstadoPartido según el enum,
 * y permite mapear de vuelta a EnumEstadoPartido.
 */
@Component
public class FactoryEstadoPartido {

    public EstadoPartido crearEstadoPartido(EnumEstadoPartido e) {
        return switch (e) {
            case PARTIDO_ARMADO        -> new PartidoArmado();
            case NECESITA_JUGADORES    -> new NecesitaJugadores();
            case CONFIRMADO            -> new Confirmado();
            case EN_JUEGO              -> new EnJuego();
            case FINALIZADO            -> new Finalizado();
            case CANCELADO             -> new Cancelado();
        };
    }

    public EnumEstadoPartido getEstadoPartidoEnum(EstadoPartido estado) {
        return switch (estado) {
            case PartidoArmado ignored       -> EnumEstadoPartido.PARTIDO_ARMADO;
            case NecesitaJugadores ignored   -> EnumEstadoPartido.NECESITA_JUGADORES;
            case Confirmado ignored          -> EnumEstadoPartido.CONFIRMADO;
            case EnJuego ignored             -> EnumEstadoPartido.EN_JUEGO;
            case Finalizado ignored          -> EnumEstadoPartido.FINALIZADO;
            case Cancelado ignored           -> EnumEstadoPartido.CANCELADO;
            default -> throw new IllegalArgumentException("Estado desconocido: " + estado.getClass());
        };
    }
}