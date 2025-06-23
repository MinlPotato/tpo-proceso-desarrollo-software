package com.uade.tpo.application.service.state.partido;

import com.uade.tpo.application.dto.EstadoDTO;
import com.uade.tpo.application.entity.Partido;

public class Finalizado implements EstadoPartido {

    @Override
    public EstadoPartido avanzar(Partido p) {
        // Ya está finalizado, no avanza más
        return this;
    }

    @Override
    public EstadoPartido cancelar(Partido p) {
        // No puede cancelarse tras finalizar
        return this;
    }

    @Override
    public String getNombre() {
        return "Finalizado";
    }

    @Override
    public String getDescripcion() {
        return "El partido ha concluido";
    }

    @Override
    public String getMensaje() {
        return "Gracias por participar";
    }

    @Override
    public EstadoDTO toDTO() {
        return new EstadoDTO(getNombre(), getDescripcion(), getMensaje());
    }
}
