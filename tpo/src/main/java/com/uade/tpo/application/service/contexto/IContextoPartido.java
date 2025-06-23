package com.uade.tpo.application.service.contexto;


import com.uade.tpo.application.dto.EstadoDTO;
import com.uade.tpo.application.entity.Partido;
import com.uade.tpo.application.enums.EnumEstadoPartido;

public interface IContextoPartido {

    /** Inicializa el contexto con el partido y un estado inicial */
    void iniciarContexto(Partido partido, EnumEstadoPartido estadoInicial);

    /** Suscribe un observer para recibir notificaciones */
    void suscribirObservador(IObservador observador);

    /** Desuscribe un observer */
    void desuscribirObservador(IObservador observador);

    /** Avanza al siguiente estado y notifica */
    boolean avanzarEstado();

    /** Cancela el estado actual y notifica */
    boolean cancelarEstado();

    /** Finaliza el estado actual y notifica */
    boolean finalizarEstado();

    /** Devuelve el partido en contexto */
    Partido getPartido();

    /** Fuerza un estado concreto (útil en pruebas) */
    void setEstado(com.uade.tpo.application.service.state.partido.EstadoPartido estado);
}