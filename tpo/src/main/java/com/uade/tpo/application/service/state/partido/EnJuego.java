package com.uade.tpo.application.service.state.partido;

import com.uade.tpo.application.dto.EstadoDTO;
import com.uade.tpo.application.entity.Partido;

public class EnJuego implements EstadoPartido {

    @Override
    public EstadoPartido avanzar(Partido p) {
        return new Finalizado();
    }

    @Override
    public EstadoPartido cancelar(Partido p) {
        return new Cancelado();
    }

    @Override
    public String getNombre() {
        return "En Juego";
    }

    @Override
    public String getDescripcion() {
        return "El partido se está llevando a cabo";
    }

    @Override
    public String getMensaje() {
        return "Disfruta el juego";
    }

    @Override
    public EstadoDTO toDTO() {
        return new EstadoDTO(getNombre(), getDescripcion(), getMensaje());
    }
}
