package com.uade.tpo.application.service.contexto;
import com.uade.tpo.application.dto.EstadoDTO;
import com.uade.tpo.application.entity.Partido;
import com.uade.tpo.application.service.factory.FactoryEstadoPartido;
import com.uade.tpo.application.service.state.partido.EstadoPartido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import com.uade.tpo.application.enums.EnumEstadoPartido;

import java.util.ArrayList;
import java.util.List;

/**
 * Contexto que mantiene el Partido y su Estado actual,
 * maneja la lista de observadores y delega avanzar/cancelar/finalizar.
 */
@Component
public class ContextoPartido {

    private final FactoryEstadoPartido factory;
    // TODO: sustituir IObservador por tu interfaz / clase concreta
    private final List<IObservador> observadores = new ArrayList<>();

    private EstadoPartido estado;
    private Partido partido;

    @Autowired
    public ContextoPartido(FactoryEstadoPartido factory) {
        this.factory = factory;
    }

    /** Inicializa partido y estado según un enum existente (por ej. PARTIDO_ARMADO). */
    public void iniciarContexto(Partido p, EnumEstadoPartido e) {
        this.partido = p;
        this.estado = factory.crearEstadoPartido(e);
    }

    /** Agrega un observador para notificarle cambios de estado */
    public void suscribirObservador(IObservador o) {
        observadores.add(o);
    }

    /** Elimina un observador de la lista */
    public void desuscribirObservador(IObservador o) {
        observadores.remove(o);
    }

    /** Avanza al siguiente estado, notifica a todos e informa éxito */
    public boolean avanzarEstado() {
        this.estado = this.estado.avanzar(partido);
        notificar(estado.toDTO());
        return true;
    }

    /** Cancela el partido (o invoca la lógica de cancelación en el estado actual) */
    public boolean cancelarEstado() {
        this.estado = this.estado.cancelar(partido);
        notificar(estado.toDTO());
        return true;
    }

    /** Finaliza el partido desde el estado actual */
    public boolean finalizarEstado() {
        this.estado = this.estado.finalizar(partido);
        notificar(estado.toDTO());
        return true;
    }

    /** Devuelve el objeto Partido en contexto */
    public Partido getPartido() {
        return partido;
    }

    /** Fija manualmente un nuevo estado (útil para restoración o pruebas) */
    public void setEstado(EstadoPartido estado) {
        this.estado = estado;
    }

    /** Notifica a los observers, pasándoles el DTO de estado */
    private void notificar(EstadoDTO dto) {
        for (IObservador o : observadores) {
            o.notificar(dto);
        }
    }
}