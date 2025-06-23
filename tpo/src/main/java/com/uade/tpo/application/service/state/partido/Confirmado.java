package com.uade.tpo.application.service.state.partido;

import com.uade.tpo.application.dto.EstadoDTO;
import com.uade.tpo.application.entity.Partido;

public class Confirmado implements EstadoPartido {

    @Override
    public EstadoPartido avanzar(Partido p) {
        return new EnJuego();
    }

    @Override
    public EstadoPartido cancelar(Partido p) {
        return new Cancelado();
    }

    @Override
    public String getNombre() {
        return "Confirmado";
    }

    @Override
    public String getDescripcion() {
        return "El partido tiene el cupo completo y está confirmado";
    }

    @Override
    public String getMensaje() {
        return "Preparado para iniciar el juego";
    }

    @Override
    public EstadoDTO toDTO() {
        return new EstadoDTO(getNombre(), getDescripcion(), getMensaje());
    }
}
