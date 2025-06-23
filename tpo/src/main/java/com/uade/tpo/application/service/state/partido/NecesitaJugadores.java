package com.uade.tpo.application.service.state.partido;

import com.uade.tpo.application.dto.EstadoDTO;
import com.uade.tpo.application.entity.Partido;

public class NecesitaJugadores implements EstadoPartido {

    @Override
    public EstadoPartido avanzar(Partido p) {
        int totalJugadores = 0;
        if (p.getEquipos() != null) {
            totalJugadores = p.getEquipos().stream()
                    .mapToInt(e -> e.getJugadores().size())
                    .sum();
        }

        if (totalJugadores >= p.getMinJugadoresNecesarios()) {
            return new Confirmado();
        }
        return this;
    }

    @Override
    public EstadoPartido cancelar(Partido p) {
        return new Cancelado();
    }

    @Override
    public String getNombre() {
        return "Necesita Jugadores";
    }

    @Override
    public String getDescripcion() {
        return "El partido requiere más jugadores antes de confirmar";
    }

    @Override
    public String getMensaje() {
        return "Inscríbete para completar el cupo";
    }

    @Override
    public EstadoDTO toDTO() {
        return new EstadoDTO(getNombre(), getDescripcion(), getMensaje());
    }
}
