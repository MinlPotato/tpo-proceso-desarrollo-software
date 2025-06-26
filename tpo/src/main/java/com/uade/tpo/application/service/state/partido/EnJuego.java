package com.uade.tpo.application.service.state.partido;

import com.uade.tpo.application.dto.EstadoDTO;
import com.uade.tpo.application.entity.Partido;
import com.uade.tpo.application.enums.EnumEstadoPartido;
import com.uade.tpo.application.service.contexto.ContextoPartido;

public class EnJuego implements EstadoPartido {

    @Override
    public void jugadorSeAgrega(ContextoPartido contextoPartido) {
        throw new IllegalStateException("No se puede agregar jugador, el partido ya comenzó.");
    }

    @Override
    public void confirmar(ContextoPartido contextoPartido) {
        throw new IllegalStateException("No se puede confirmar, el partido ya comenzó.");
    }

    @Override
    public void iniciar(ContextoPartido contextoPartido) {
        throw new IllegalStateException("No se puede iniciar, el partido ya comenzó.");
    }

    @Override
    public void cancelar(ContextoPartido contextoPartido) {
        throw new IllegalStateException("No se puede cancelar, el partido ya comenzó.");
    }

    @Override
    public void finalizar(ContextoPartido contextoPartido) {
        Partido partido = contextoPartido.getPartido();

        contextoPartido.setEstado(new Finalizado());
        partido.setEstado(EnumEstadoPartido.FINALIZADO);
        contextoPartido.notificar();
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
