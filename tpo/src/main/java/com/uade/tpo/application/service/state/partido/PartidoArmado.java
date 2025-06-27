package com.uade.tpo.application.service.state.partido;


import com.uade.tpo.application.dto.EstadoDTO;
import com.uade.tpo.application.entity.Partido;
import com.uade.tpo.application.enums.EnumEstadoPartido;
import com.uade.tpo.application.service.partido.ContextoPartido;

public class PartidoArmado implements EstadoPartido {

    @Override
    public void jugadorSeAgrega(ContextoPartido contextoPartido) {
        throw new IllegalStateException("No se puede agregar jugador, todos los equipos del partido ya están llenos.");
    }

    @Override
    public void jugadorSeElimina(ContextoPartido contextoPartido) {
        Partido partido = contextoPartido.getPartido();

        contextoPartido.setEstado(new NecesitaJugadores());
        partido.setEstado(EnumEstadoPartido.NECESITA_JUGADORES);
        contextoPartido.notificar();
    }

    @Override
    public void confirmar(ContextoPartido contextoPartido) {
        Partido partido = contextoPartido.getPartido();

        contextoPartido.setEstado(new Confirmado());
        partido.setEstado(EnumEstadoPartido.CONFIRMADO);
        contextoPartido.notificar();
    }

    @Override
    public void iniciar(ContextoPartido contextoPartido) {
        Partido partido = contextoPartido.getPartido();
        throw new IllegalStateException("No se puede iniciar Partido hasta que llegue la fecha: " + partido.getHorario());
    }

    @Override
    public void cancelar(ContextoPartido contextoPartido) {
        Partido partido = contextoPartido.getPartido();

        contextoPartido.setEstado(new Cancelado());
        partido.setEstado(EnumEstadoPartido.CANCELADO);
        contextoPartido.notificar();
    }

    @Override
    public void finalizar(ContextoPartido contextoPartido) {
        throw new IllegalStateException("No se puede finalizar Partido.");
    }

    @Override
    public String getNombre() {
        return "Partido Armado";
    }

    @Override
    public String getDescripcion() {
        return "El partido ha sido armado!";
    }

    @Override
    public String getMensaje() {
        return "Todos los equipos están completos!";
    }

    @Override
    public EstadoDTO toDTO() {
        return new EstadoDTO(getNombre(), getDescripcion(), getMensaje());
    }
}