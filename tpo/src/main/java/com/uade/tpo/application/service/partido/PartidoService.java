package com.uade.tpo.application.service.partido;

import com.uade.tpo.application.dto.*;
import com.uade.tpo.application.entity.Equipo;
import com.uade.tpo.application.entity.Jugador;
import com.uade.tpo.application.entity.Partido;
import com.uade.tpo.application.enums.EnumEstadoPartido;
import com.uade.tpo.application.repository.DeporteRepository;
import com.uade.tpo.application.repository.JugadorRepository;
import com.uade.tpo.application.repository.PartidoRepository;
import com.uade.tpo.application.service.contexto.ContextoPartido;
import com.uade.tpo.application.service.contexto.IContextoPartido;
import com.uade.tpo.application.service.contexto.IObservador;
import com.uade.tpo.application.service.equipo.EquipoService;
import com.uade.tpo.application.service.equipo.IEquipoService;
import com.uade.tpo.application.service.factory.FactoryEstadoPartido;

import com.uade.tpo.application.service.notificador.INotificadorService;
import com.uade.tpo.application.service.state.partido.EstadoPartido;
import com.uade.tpo.application.service.strategy.partido.FiltrarPorHistorial;
import com.uade.tpo.application.service.strategy.partido.FiltrarPorNivel;
import com.uade.tpo.application.service.strategy.partido.FiltrarPorUbicacion;
import com.uade.tpo.application.service.strategy.partido.StrategyFiltrarPartido;

import jakarta.persistence.EntityNotFoundException;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PartidoService implements IPartidoService {

    @Autowired
    private PartidoRepository partidoRepository;
    @Autowired
    private JugadorRepository jugadorRepository;
    @Autowired
    private DeporteRepository deporteRepository;

    @Autowired
    private IEquipoService equipoService;

    @Autowired
    private ContextoPartido contextoPartido;
    @Autowired
    private FactoryEstadoPartido factoryEstadoPartido;
    @Autowired
    private INotificadorService notificadorService; // inyecta NotificadorService

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
        p.setEstado(EnumEstadoPartido.NECESITA_JUGADORES);
        p.setCantidadJugadoresPorEquipo(dto.getCantidadJugadoresPorEquipo());
        p.setCantidadEquipos(dto.getCantidadEquipos());
        p.setNivelesJugadores(dto.getNivelesJugadores());
        Partido saved = partidoRepository.save(p);

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
    @Transactional
    public PartidoDTO agregarJugador(Long partidoId, AgregarJugadorDTO agregarJugadorDTO) {
        Partido partido = partidoRepository.findById(partidoId)
            .orElseThrow(() -> new EntityNotFoundException("Partido no encontrado: " + partidoId));
        Jugador jugador = jugadorRepository.findById(agregarJugadorDTO.getJugadorId())
            .orElseThrow(() -> new EntityNotFoundException("Jugador no encontrado: " + agregarJugadorDTO.getJugadorId()));

        Equipo equipo = partido.getEquipos().get(agregarJugadorDTO.getNumeroEquipo());

        equipoService.unirseEquipo(equipo.getId(), jugador.getId());

        contextoPartido.iniciarContexto(partido, partido.getEstado());
        contextoPartido.jugadorSeAgrega();

        return toDTO(partidoRepository.save(contextoPartido.getPartido()));
    }

    @Override
    public void desuscribirObservador(Long partidoId, AgregarJugadorDTO agregarJugadorDTO) {
        // TODO: Hacer logica de desuscripcion

        Partido partido = partidoRepository.findById(partidoId)
            .orElseThrow(() -> new EntityNotFoundException("Partido no encontrado: " + partidoId));
        contextoPartido.iniciarContexto(partido, partido.getEstado());
    }

    @Override
    public PartidoDTO confirmarPartido(Long id) {
        Partido partido = partidoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Partido no encontrado: " + id));

        contextoPartido.iniciarContexto(partido, partido.getEstado());
        contextoPartido.confirmar();

        return toDTO(partidoRepository.save(contextoPartido.getPartido()));
    }

    @Transactional
    @Scheduled(cron = "0 * * * * *") // Cron corre cada 60 segundos
    private void revisarPartidosParaIniciar() {
        List<Partido> partidosParaIniciar = partidoRepository.findByEstadoAndHorarioLessThanEqual(
            EnumEstadoPartido.CONFIRMADO,
            LocalDateTime.now()
        );

        for (Partido partido : partidosParaIniciar) {
            iniciarPartido(partido);
        }
    }

    @Override
    public void iniciarPartido(Partido partido) {
        contextoPartido.iniciarContexto(partido, partido.getEstado());
        contextoPartido.iniciar();
        partidoRepository.save(contextoPartido.getPartido());
    }

    @Override
    public PartidoDTO cancelarPartido(Long id) {
        Partido partido = partidoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Partido no encontrado: " + id));

        contextoPartido.iniciarContexto(partido, partido.getEstado());
        contextoPartido.cancelar();
        return toDTO(partidoRepository.save(contextoPartido.getPartido()));
    }

    public List<PartidoDTO> filtrar(Long jugadorID, String tipoDeFiltro) {
        Jugador jugador = jugadorRepository.findById(jugadorID)
            .orElseThrow(() -> new EntityNotFoundException("Jugador no encontrado: " + jugadorID));

        StrategyFiltrarPartido strategy;
        return switch (tipoDeFiltro.toLowerCase()) {
            case "historial" -> {
                strategy = new FiltrarPorHistorial();
                yield strategy.filtrar(jugador, partidoRepository).stream().map(this::toDTO).toList();
            }
            case "nivel" -> {
                strategy = new FiltrarPorNivel();
                yield strategy.filtrar(jugador, partidoRepository).stream().map(this::toDTO).toList();
            }
            case "ubicacion" -> {
                strategy = new FiltrarPorUbicacion();
                yield strategy.filtrar(jugador, partidoRepository).stream().map(this::toDTO).toList();
            }
            default -> throw new IllegalArgumentException("Tipo de filtro no soportado: " + tipoDeFiltro);
        };
    }

    private PartidoDTO toDTO(Partido partido) {
        // Mapear estado si está presente
        EstadoDTO estadoDTO = null;
        EstadoPartido estado = factoryEstadoPartido.crearEstadoPartido(partido.getEstado());
        if (estado != null) {
            estadoDTO = new EstadoDTO(
                estado.getNombre(),
                estado.getDescripcion(),
                estado.getMensaje()
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
