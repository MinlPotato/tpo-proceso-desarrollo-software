package com.uade.tpo.application.service.state.partido;

import com.uade.tpo.application.dto.EstadoDTO;
import com.uade.tpo.application.entity.Partido;
import com.uade.tpo.application.service.contexto.ContextoPartido;
import com.uade.tpo.application.service.contexto.IContextoPartido;

public class Cancelado implements EstadoPartido {

    @Override
    public void jugadorSeAgrega(ContextoPartido contextoPartido) {
        throw new IllegalStateException("No se puede agregar jugador, el partido fue cancelado.");
    }

    @Override
    public void confirmar(ContextoPartido contextoPartido) {
        throw new IllegalStateException("No se puede confirmar, el partido fue cancelado.");
    }

    @Override
    public void iniciar(ContextoPartido contextoPartido) {
        throw new IllegalStateException("No se puede iniciar, el partido fue cancelado.");
    }

    @Override
    public void cancelar(ContextoPartido contextoPartido) {
        throw new IllegalStateException("No se puede cancelar, el partido ya fue cancelado.");
    }

    @Override
    public void finalizar(ContextoPartido contextoPartido) {
        throw new IllegalStateException("No se puede finalizar, el partido fue cancelado.");
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
