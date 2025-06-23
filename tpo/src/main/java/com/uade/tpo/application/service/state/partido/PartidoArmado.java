package com.uade.tpo.application.service.state.partido;


import com.uade.tpo.application.dto.EstadoDTO;
import com.uade.tpo.application.entity.Partido;

public class PartidoArmado implements EstadoPartido {

    @Override
    public EstadoPartido avanzar(Partido p) {
        // Pasa a NecesitaJugadores
        return new NecesitaJugadores();
    }

    @Override
    public EstadoPartido cancelar(Partido p) {
        return new Cancelado();
    }

    @Override
    public String getNombre() {
        return "Partido Armado";
    }

    @Override
    public String getDescripcion() {
        return "El partido ha sido armado y espera inscripciones";
    }

    @Override
    public String getMensaje() {
        return "Listo para recibir jugadores";
    }

    @Override
    public EstadoDTO toDTO() {
        return new EstadoDTO(getNombre(), getDescripcion(), getMensaje());
    }
}