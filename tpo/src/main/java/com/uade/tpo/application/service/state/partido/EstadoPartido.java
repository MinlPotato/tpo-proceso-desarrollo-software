package com.uade.tpo.application.service.state.partido;

import com.uade.tpo.application.dto.EstadoDTO;
import com.uade.tpo.application.service.partido.ContextoPartido;

/**
 * Interfaz común para los distintos estados de un Partido.
 * Cada implementación debe encargarse de su propia lógica de transición
 * y de exponer su representación como EstadoDTO.
 */
public interface EstadoPartido {

    /**
     * Jugador se agrega a un equipo.
     * @param contextoPartido el partido en contexto
     */
    void jugadorSeAgrega(ContextoPartido contextoPartido);

    /**
     * Jugador se elimina de un equipo.
     * @param contextoPartido el partido en contexto
     */
    void jugadorSeElimina(ContextoPartido contextoPartido);

    /**
     * Cuando el partido esta para comenzar, se llama a este metodo.
     * @param contextoPartido el partido en contexto
     */
    void confirmar(ContextoPartido contextoPartido);

    /**
     * Dar inicio al partido solo si la fecha es la indicada y es posible iniciar.
     * @param contextoPartido
     */
    void iniciar(ContextoPartido contextoPartido);

    /**
     * Cancelacion del partido.
     * @param contextoPartido el partido en contexto
     */
    void cancelar(ContextoPartido contextoPartido);

    /**
     * Transición directa a finalización.
     * Por defecto devuelve un nuevo Finalizado, pero puede
     * sobrescribirse en estados que requieran lógica particular.
     * @param p el partido en contexto
     * @return la instancia de EstadoPartido resultante
     */
    void finalizar(ContextoPartido contextoPartido);

    /**
     * Representación DTO de este estado (nombre, descripción y mensaje)
     * para enviar al cliente o notificar.
     * @return un EstadoDTO describiendo este estado
     */
    EstadoDTO toDTO();

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
