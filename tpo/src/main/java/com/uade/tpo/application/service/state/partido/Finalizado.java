package com.uade.tpo.application.service.state.partido;

import com.uade.tpo.application.dto.EstadoDTO;
import com.uade.tpo.application.entity.Partido;
import com.uade.tpo.application.service.contexto.ContextoPartido;

public class Finalizado implements EstadoPartido {

    @Override
    public void jugadorSeAgrega(ContextoPartido contextoPartido) {
        throw new IllegalStateException("No se puede agregar jugador, el partido ya finalizó.");
    }

    @Override
    public void confirmar(ContextoPartido contextoPartido) {
        throw new IllegalStateException("No se puede confirmar, el partido ya finalizó.");
    }

    @Override
    public void iniciar(ContextoPartido contextoPartido) {
        throw new IllegalStateException("No se puede iniciar, el partido ya finalizó.");
    }

    @Override
    public void cancelar(ContextoPartido contextoPartido) {
        throw new IllegalStateException("No se puede cancelar, el partido ya finalizó.");
    }

    @Override
    public void finalizar(ContextoPartido contextoPartido) {
        throw new IllegalStateException("No se puede finalizar, el partido ya finalizó.");
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
