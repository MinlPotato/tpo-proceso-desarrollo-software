package com.uade.tpo.application.service.state.partido;

import com.uade.tpo.application.dto.EstadoDTO;
import com.uade.tpo.application.entity.Partido;
import com.uade.tpo.application.enums.EnumEstadoPartido;
import com.uade.tpo.application.service.contexto.ContextoPartido;
import com.uade.tpo.application.service.contexto.IContextoPartido;

public class NecesitaJugadores implements EstadoPartido {

    @Override
    public void jugadorSeAgrega(ContextoPartido contextoPartido) {
        Partido partido = contextoPartido.getPartido();

        int totalJugadores = contextoPartido.getObservadores().size();
        int cantidadTotal = partido.getCantidadEquipos() * partido.getCantidadJugadoresPorEquipo();

        if (totalJugadores >= cantidadTotal) {
            contextoPartido.setEstado(new PartidoArmado());
            partido.setEstado(EnumEstadoPartido.PARTIDO_ARMADO);
            contextoPartido.notificar();
        }
    }

    @Override
    public void confirmar(ContextoPartido contextoPartido) {
        throw new IllegalStateException("No se puede confirmar Partido hasta que se armen todos los equipos.");
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

    public void finalizar(ContextoPartido contextoPartido) {
        throw new IllegalStateException("No se puede finalizar Partido.");
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
