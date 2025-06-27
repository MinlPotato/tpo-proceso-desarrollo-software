package com.uade.tpo.application.service.contexto;
import com.uade.tpo.application.dto.NotificacionDTO;
import com.uade.tpo.application.entity.Equipo;
import com.uade.tpo.application.entity.Jugador;
import com.uade.tpo.application.entity.Partido;
import com.uade.tpo.application.factory.FactoryEstadoPartido;
import com.uade.tpo.application.service.notificador.INotificadorService;
import com.uade.tpo.application.service.state.partido.EstadoPartido;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.List;

/**
 * Contexto que mantiene el Partido y su Estado actual,
 * maneja la lista de observadores y delega avanzar/cancelar/finalizar.
 */
@Getter
@Setter
@Component
public class ContextoPartido  {

    private FactoryEstadoPartido factory;
    private List<Jugador> observadores;
    private EstadoPartido estado;
    private Partido partido;
    private INotificadorService notificadorService;

    @Autowired
    public ContextoPartido(FactoryEstadoPartido factory, INotificadorService notificador) {
        this.factory = factory;
        this.notificadorService = notificador;
    }

    public void iniciarContexto(Partido partido, List<Equipo> equipos) {
        this.partido = partido;
        this.estado = factory.crearEstadoPartido(partido.getEstado());
        this.observadores = partido.getEquipos()
            .stream()
            .flatMap(equipo -> equipo.getJugadores().stream())
            .toList();
    }

    public boolean jugadorSeAgrega() {
        this.estado.jugadorSeAgrega(this);
        return true;
    }

    public boolean confirmar() {
        this.estado.confirmar(this);
        return true;
    }

    public boolean iniciar() {
        this.estado.iniciar(this);
        return true;
    }

    /** Cancela el partido (o invoca la lógica de cancelación en el estado actual) */
    public boolean cancelar() {
        this.estado.cancelar(this);
        return true;
    }

    /** Finaliza el partido desde el estado actual */
    public boolean finalizar() {
        this.estado.finalizar(this);
        return true;
    }

    /** Notifica a los observers, pasándoles el DTO */
    public void notificar() {
        for (Jugador jugador : observadores) {

            NotificacionDTO notificacion = new NotificacionDTO(
                estado.getNombre() + ": " + estado.getMensaje(),
                estado.getDescripcion(),
                jugador.getEmail()
            );

            notificadorService.enviarNotificaion(jugador.getFormaNotificar(), notificacion);
        }
    }
}