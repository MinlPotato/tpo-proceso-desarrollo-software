package com.uade.tpo.application.service.partido;

import com.uade.tpo.application.dto.*;
import com.uade.tpo.application.entity.Equipo;
import com.uade.tpo.application.entity.Jugador;
import com.uade.tpo.application.entity.Partido;
import com.uade.tpo.application.enums.EnumEstadoPartido;
import com.uade.tpo.application.repository.DeporteRepository;
import com.uade.tpo.application.repository.EquipoRepository;
import com.uade.tpo.application.repository.JugadorRepository;
import com.uade.tpo.application.repository.PartidoRepository;
import com.uade.tpo.application.service.contexto.IContextoPartido;
import com.uade.tpo.application.service.contexto.IObservador;
import com.uade.tpo.application.service.factory.FactoryEstadoPartido;

import com.uade.tpo.application.service.jugador.JugadorService;
import com.uade.tpo.application.service.state.partido.PartidoArmado;
import com.uade.tpo.application.service.strategy.partido.FiltrarPorHistorial;
import com.uade.tpo.application.service.strategy.partido.FiltrarPorNivel;
import com.uade.tpo.application.service.strategy.partido.FiltrarPorUbicacion;
import com.uade.tpo.application.service.strategy.partido.StrategyFiltrarPartido;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebProperties.Resources.Chain.Strategy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PartidoService implements IPartidoService {

    @Autowired private PartidoRepository partidoRepository;
    @Autowired private JugadorRepository jugadorRepository;
    @Autowired private DeporteRepository deporteRepository;

    @Autowired private IContextoPartido contextoPartido;
    @Autowired private FactoryEstadoPartido factoryEstadoPartido;
    @Autowired private IObservador notificadorService; // inyecta NotificadorService

    @Override
    public List<PartidoDTO> getPartidos() {
        return partidoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PartidoDTO getPartidoById(Long id) {
        Partido p = partidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Partido no encontrado: " + id));
        return toDTO(p);
    }

    @Override
    public PartidoDTO createPartido(PartidoCreateDTO dto) {
        Partido p = new Partido();
        p.setDuracion(dto.getDuracion());
        p.setHorario(dto.getHorario());
        p.setUbicacion(dto.getUbicacion());
        p.setCreador(jugadorRepository.findById(dto.getIdCreador())
                .orElseThrow(() -> new EntityNotFoundException("Jugador no encontrado: " + dto.getIdCreador())));
        p.setDeporte(deporteRepository.findById(dto.getIdDeporte())
                .orElseThrow(() -> new EntityNotFoundException("Deporte no encontrado: " + dto.getIdDeporte())));
        // Inicializo el estado
        p.setEstado(factoryEstadoPartido.crearEstadoPartido(EnumEstadoPartido.PARTIDO_ARMADO));
        Partido saved = partidoRepository.save(p);
        p.setCantidadJugadoresPorEquipo(dto.getCantidadJugadoresPorEquipo());

        return toDTO(saved);

    }

    @Override
    public PartidoDTO updatePartido(Long id, PartidoDTO dto) {
        Partido p = partidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Partido no encontrado: " + id));
        p.setDuracion(dto.getDuracion());
        p.setHorario(dto.getHorario());
        p.setUbicacion(dto.getUbicacion());
        Partido updated = partidoRepository.save(p);
        return toDTO(updated);
    }

    @Override
    public void deletePartido(Long id) {
        if (!partidoRepository.existsById(id)) {
            throw new EntityNotFoundException("Partido no encontrado: " + id);
        }
        partidoRepository.deleteById(id);
    }

    @Override
    public void suscribirObservador(Long partidoId, IObservador observador) {
        Partido p = partidoRepository.findById(partidoId)
                .orElseThrow(() -> new EntityNotFoundException("Partido no encontrado: " + partidoId));
        contextoPartido.iniciarContexto(p,
                factoryEstadoPartido.getEstadoPartidoEnum(p.getEstado()));
        contextoPartido.suscribirObservador(observador);
    }

    @Override
    public void desuscribirObservador(Long partidoId, IObservador observador) {
        Partido p = partidoRepository.findById(partidoId)
                .orElseThrow(() -> new EntityNotFoundException("Partido no encontrado: " + partidoId));
        contextoPartido.iniciarContexto(p,
                factoryEstadoPartido.getEstadoPartidoEnum(p.getEstado()));
        contextoPartido.desuscribirObservador(observador);
    }

    @Override
    public boolean avanzarPartido(Long id) {
        Partido p = partidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Partido no encontrado: " + id));

        contextoPartido.iniciarContexto(p,
                factoryEstadoPartido.getEstadoPartidoEnum(p.getEstado()));
        contextoPartido.suscribirObservador(notificadorService);
        boolean exito = contextoPartido.avanzarEstado();

        p.setEstado(contextoPartido.getPartido().getEstado());
        partidoRepository.save(p);
        return exito;
    }

    @Override
    public boolean cancelarPartido(Long id) {
        Partido p = partidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Partido no encontrado: " + id));

        contextoPartido.iniciarContexto(p,
                factoryEstadoPartido.getEstadoPartidoEnum(p.getEstado()));
        contextoPartido.suscribirObservador(notificadorService);
        boolean exito = contextoPartido.cancelarEstado();

        p.setEstado(contextoPartido.getPartido().getEstado());
        partidoRepository.save(p);
        return exito;
    }

    public List<PartidoDTO> filtrar(Long jugadorID, String tipoDeFiltro ){
        Jugador jugador = jugadorRepository.findById(jugadorID)
                .orElseThrow(() -> new EntityNotFoundException("Jugador no encontrado: " + jugadorID));
        JugadorDTO jugadorDTO = new JugadorDTO(jugador.getId(), jugador.getNombre(), jugador.getEmail(), jugador.getUbicacion(), jugador.getNiveles()
                .stream()
                .map(nivel -> new NivelDTO(nivel.getId(), nivel.getJugador().getId(), nivel.getDeporte().getId(), nivel.getNivel(), nivel.getFavorito()))
                .collect(Collectors.toList()));
        StrategyFiltrarPartido strategy;
        switch (tipoDeFiltro.toLowerCase()) {
            case "historial":
                strategy = new FiltrarPorHistorial();
                return strategy.filtrar(jugadorDTO, partidoRepository).stream().map(this::toDTO).toList();
            case "nivel":
                strategy = new FiltrarPorNivel();
                return strategy.filtrar(jugadorDTO, partidoRepository).stream().map(this::toDTO).toList();
            case "ubicacion":
                strategy = new FiltrarPorUbicacion();
                return strategy.filtrar(jugadorDTO, partidoRepository).stream().map(this::toDTO).toList();
            default:
                throw new IllegalArgumentException("Tipo de filtro no soportado: " + tipoDeFiltro);
        }
    }

private PartidoDTO toDTO(Partido partido) {
    // Mapear estado si está presente
    EstadoDTO estadoDTO = null;
    if (partido.getEstado() != null) {
        estadoDTO = new EstadoDTO(
            partido.getEstado().getNombre(),
            partido.getEstado().getDescripcion(),
            partido.getEstado().getMensaje()
        );
    }

    // Calcular cantidad de equipos
    List<Equipo> equipos = partido.getEquipos();
    int cantidadEquipos = (equipos != null) ? equipos.size() : 0;

    // Calcular cantidad de jugadores por equipo (asumimos que todos tienen la misma cantidad)
    int cantidadJugadoresPorEquipo = 0;
    if (equipos != null && !equipos.isEmpty() && equipos.get(0).getJugadores() != null) {
        cantidadJugadoresPorEquipo = equipos.get(0).getJugadores().size();
    }

    return new PartidoDTO(
        partido.getId(),
        (partido.getCreador() != null) ? partido.getCreador().getId() : null,
        (partido.getDeporte() != null) ? partido.getDeporte().getId() : null,
        partido.getDuracion(),
        partido.getHorario(),
        partido.getUbicacion(),
        cantidadEquipos,
        cantidadJugadoresPorEquipo,
        estadoDTO,
        equipos != null ? equipos.stream()
            .map(equipo -> new EquipoDTO(
                equipo.getId(),
                equipo.getNombre(),
                equipo.getPartido() != null ? equipo.getPartido().getId() : null,
                equipo.getJugadores() != null ? equipo.getJugadores().stream()
                    .map(Jugador::getId)
                    .toList() : List.of()
            )).toList() : List.of(),
        partido.getNivelesJugadores()
    );
}


}
