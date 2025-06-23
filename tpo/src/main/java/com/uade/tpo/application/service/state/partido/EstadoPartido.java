package com.uade.tpo.application.service.state.partido;

import com.uade.tpo.application.dto.EstadoDTO;
import com.uade.tpo.application.entity.Partido;

/**
 * Interfaz común para los distintos estados de un Partido.
 * Cada implementación debe encargarse de su propia lógica de transición
 * y de exponer su representación como EstadoDTO.
 */
public interface EstadoPartido {

    /**
     * Transición al siguiente estado según la lógica de este estado.
     * @param p el partido en contexto
     * @return la nueva instancia de EstadoPartido
     */
    EstadoPartido avanzar(Partido p);

    /**
     * Transición a estado cancelado o el apropiado según la lógica de este estado.
     * @param p el partido en contexto
     * @return la nueva instancia de EstadoPartido
     */
    EstadoPartido cancelar(Partido p);

    /**
     * Representación DTO de este estado (nombre, descripción y mensaje)
     * para enviar al cliente o notificar.
     * @return un EstadoDTO describiendo este estado
     */
    EstadoDTO toDTO();

    /**
     * Transición directa a finalización.
     * Por defecto devuelve un nuevo Finalizado, pero puede
     * sobrescribirse en estados que requieran lógica particular.
     * @param p el partido en contexto
     * @return la instancia de EstadoPartido resultante
     */
    default EstadoPartido finalizar(Partido p) {
        return new Finalizado();
    }

    /**
     * Nombre legible de este estado.
     */
    String getNombre();

    /**
     * Descripción más detallada de este estado.
     */
    String getDescripcion();

    /**
     * Mensaje o hint que se puede mostrar al usuario.
     */
    String getMensaje();
}
