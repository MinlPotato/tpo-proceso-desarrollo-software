package com.uade.tpo.application.service.state.partido;

import com.uade.tpo.application.dto.EstadoDTO;
import com.uade.tpo.application.entity.Partido;
import com.uade.tpo.application.enums.EnumEstadoPartido;
import com.uade.tpo.application.service.partido.ContextoPartido;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class Confirmado implements EstadoPartido {

    @Override
    public void jugadorSeAgrega(ContextoPartido contextoPartido) {
        throw new IllegalStateException("No se puede agregar jugador, el partido ya fue confirmado.");
    }

    @Override
    public void jugadorSeElimina(ContextoPartido contextoPartido) {
        throw new IllegalStateException("No se puede eliminar jugador de un equipo, el partido ya fue confirmado.");
    }

    @Override
    public void confirmar(ContextoPartido contextoPartido) {
        throw new IllegalStateException("El partido ya fue confirmado.");
    }

    @Override
    public void iniciar(ContextoPartido contextoPartido) {
        Partido partido = contextoPartido.getPartido();
        LocalDateTime horarioDeInicio = partido.getHorario()
            .atZone(ZoneId.systemDefault())
            .withZoneSameInstant(ZoneOffset.UTC)
            .toLocalDateTime();

        if (LocalDateTime.now(Clock.systemUTC()).isAfter(horarioDeInicio)) {
            contextoPartido.setEstado(new EnJuego());
            partido.setEstado(EnumEstadoPartido.EN_JUEGO);
            contextoPartido.notificar();
        } else {
            throw new IllegalStateException("No se puede iniciar Partido hasta que llegue la fecha: " + partido.getHorario());
        }
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
        return "Partido confirmado";
    }

    @Override
    public String getDescripcion() {
        return "El partido tiene el cupo completo y está confirmado";
    }

    @Override
    public String getMensaje() {
        return "Preparado para iniciar el juego cuando llegue la fecha,";
    }

    @Override
    public EstadoDTO toDTO() {
        return new EstadoDTO(getNombre(), getDescripcion(), getMensaje());
    }
}
