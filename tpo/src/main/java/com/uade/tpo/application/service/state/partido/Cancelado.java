package com.uade.tpo.application.service.state.partido;

import com.uade.tpo.application.dto.EstadoDTO;
import com.uade.tpo.application.entity.Partido;

public class Cancelado implements EstadoPartido {

    @Override
    public EstadoPartido avanzar(Partido p) {
        // Estado terminal
        return this;
    }

    @Override
    public EstadoPartido cancelar(Partido p) {
        return this;
    }

    @Override
    public String getNombre() {
        return "Cancelado";
    }

    @Override
    public String getDescripcion() {
        return "El partido ha sido cancelado";
    }

    @Override
    public String getMensaje() {
        return "Lo sentimos, el partido no se llevará a cabo";
    }

    @Override
    public EstadoDTO toDTO() {
        return new EstadoDTO(getNombre(), getDescripcion(), getMensaje());
    }
}
