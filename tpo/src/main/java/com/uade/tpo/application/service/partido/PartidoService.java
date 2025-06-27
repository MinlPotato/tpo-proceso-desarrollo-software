package com.uade.tpo.application.service.partido;

import com.uade.tpo.application.dto.*;
import com.uade.tpo.application.entity.*;
import com.uade.tpo.application.enums.EnumEstadoPartido;
import com.uade.tpo.application.enums.TipoFiltro;
import com.uade.tpo.application.repository.PartidoRepository;
import com.uade.tpo.application.service.deporte.IDeporteService;
import com.uade.tpo.application.service.equipo.IEquipoService;

import com.uade.tpo.application.service.jugador.IJugadorService;
import com.uade.tpo.application.service.nivel.INivelService;
import com.uade.tpo.application.service.notificador.INotificadorService;
import com.uade.tpo.application.service.security.UserService;
import com.uade.tpo.application.service.strategy.partido.FiltrarPorHistorial;
import com.uade.tpo.application.service.strategy.partido.FiltrarPorNivel;
import com.uade.tpo.application.service.strategy.partido.FiltrarPorUbicacion;
import com.uade.tpo.application.service.strategy.partido.StrategyFiltrarPartido;

import jakarta.persistence.EntityNotFoundException;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PartidoService implements IPartidoService {

    @Autowired
    private PartidoRepository partidoRepository;

    @Autowired
    private IEquipoService equipoService;
    @Autowired
    private INivelService nivelService;
    @Autowired
    private IDeporteService deporteService;
    @Autowired
    private IJugadorService jugadorService;
    @Autowired
    private UserService userService;

    @Autowired
    private ContextoPartido contextoPartido;
    @Autowired
    private INotificadorService notificadorService; // inyecta NotificadorService

    @Override
    public List<Partido> getPartidos() {
        return partidoRepository.findAll();
    }

    @Override
    public Partido getPartidoById(Long id) {
        return partidoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Partido no encontrado: " + id));
    }

    @Override
    @Transactional
    public Partido createPartido(PartidoCreateDTO dto) {

        Jugador jugadorCreador = jugadorService.getJugadorById(dto.getIdCreador());
        User user = userService.getCurrentUser();
        jugadorService.validarUsuario(jugadorCreador, user);

        Deporte deporte = deporteService.getDeporteById(dto.getIdDeporte());
        Partido partido = new Partido(dto, jugadorCreador, deporte);
        Partido saved = partidoRepository.save(partido);

        for (int i = 0; i < dto.getCantidadEquipos(); i++) {
            Equipo equipo = equipoService.createEquipo(
                partido,
                new EquipoCreateDTO(
                    "Equipo " + (i+1)
                )
            );
            saved.getEquipos().add(equipo);
        }

        List<Jugador> jugadoresInteresados = nivelService.buscarJugadoresComoFavorito(deporte);

        jugadoresInteresados.forEach(jugador -> {
            NotificacionDTO notificacionDTO = new NotificacionDTO(
                "Partido para vos!",
                "Se creó un partido de " + deporte.getNombre() + ".",
                jugador.getEmail()
            );
            notificadorService.enviarNotificaion(jugador.getFormaNotificar(), notificacionDTO);
        });

        return partidoRepository.save(saved);
    }

    @Override
    public Partido updatePartido(Long id, PartidoDTO dto) {

        Partido partido = partidoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Partido no encontrado: " + id));

        Jugador jugador = jugadorService.getJugadorById(partido.getCreador().getId());
        User user = userService.getCurrentUser();
        jugadorService.validarUsuario(jugador, user);

        partido.setDuracion(dto.getDuracion());
        partido.setHorario(dto.getHorario());
        partido.setUbicacion(dto.getUbicacion());

        return partidoRepository.save(partido);
    }

    @Override
    public void deletePartido(Long id) {

        Partido partido = getPartidoById(id);
        Jugador jugador = jugadorService.getJugadorById(partido.getCreador().getId());
        User user = userService.getCurrentUser();
        jugadorService.validarUsuario(jugador, user);

        partidoRepository.deleteById(id);
    }

       @Override
    @Transactional
    public Partido agregarJugador(Long partidoId, AgregarJugadorDTO agregarJugadorDTO) {
        Partido partido = this.getPartidoById(partidoId);
        Jugador jugador = jugadorService.getJugadorById(agregarJugadorDTO.getJugadorId());
        List<Equipo> equipos = partido.getEquipos();

        equipos.forEach(equipo -> {
            if (equipo.getJugadores().contains(jugador)) {
                throw new IllegalArgumentException("El jugador ya se encuentra en el equipo.");
            }
        });
        if(partido.getDeporte().getId() != jugador.getNiveles().listIterator().next().getDeporte().getId()) {
            throw new IllegalArgumentException("El deporte del jugador no coincide con el del partido.");
        }
        if (partido.getNivelesJugadores().stream()
            .noneMatch(nivel -> nivel.equals(jugador.getNiveles().listIterator().next().getNivel()))) {
            throw new IllegalArgumentException("El jugador no tiene un nivel compatible con el partido.");
        }
        Equipo equipoAUnirse = equipos.get(agregarJugadorDTO.getNumeroEquipo()).getJugadores().size() < partido.getCantidadJugadoresPorEquipo()
            ? equipos.get(agregarJugadorDTO.getNumeroEquipo())
            : null;

        equipoService.unirseEquipo(equipoAUnirse, jugador);

        contextoPartido.iniciarContexto(partido);
        contextoPartido.jugadorSeAgrega();

        return partidoRepository.save(contextoPartido.getPartido());
    }



    @Override
    public Partido eliminarJugador(Long partidoId, AgregarJugadorDTO agregarJugadorDTO) {
        Partido partido = this.getPartidoById(partidoId);
        Jugador jugador = jugadorService.getJugadorById(agregarJugadorDTO.getJugadorId());

        Equipo equipo = partido.getEquipos().get(agregarJugadorDTO.getNumeroEquipo());

        equipoService.abandonarEquipo(equipo, jugador);

        contextoPartido.iniciarContexto(partido);
        contextoPartido.jugadorSeElimina();

        return partidoRepository.save(contextoPartido.getPartido());
    }

    @Override
    public Partido confirmarPartido(Long id) {
        Partido partido = partidoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Partido no encontrado: " + id));

        Jugador jugador = jugadorService.getJugadorById(partido.getCreador().getId());
        User user = userService.getCurrentUser();
        jugadorService.validarUsuario(jugador, user);

        contextoPartido.iniciarContexto(partido);
        contextoPartido.confirmar();

        return partidoRepository.save(contextoPartido.getPartido());
    }

    @Scheduled(cron = "0 * * * * *") // Cron corre cada 60 segundos
    @Transactional
    protected void revisarPartidosParaIniciar() {
        List<Partido> partidosParaIniciar = partidoRepository.findByEstado(
            EnumEstadoPartido.CONFIRMADO
        );

        for (Partido partido : partidosParaIniciar) {
            try {
                iniciarPartido(partido);
            } catch (IllegalStateException e) {
                log.info(e.getMessage(), partido.getId(), partido.getHorario());
            }
        }
    }

    @Override
    @Transactional
    public void iniciarPartido(Partido partido) {
        contextoPartido.iniciarContexto(partido);
        contextoPartido.iniciar();
        partidoRepository.save(contextoPartido.getPartido());
    }

    @Override
    @Transactional
    public Partido finalizarPartido(Long id) {
        Partido partido = partidoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Partido no encontrado: " + id));

        Jugador jugador = jugadorService.getJugadorById(partido.getCreador().getId());
        User user = userService.getCurrentUser();
        jugadorService.validarUsuario(jugador, user);

        contextoPartido.iniciarContexto(partido);
        contextoPartido.finalizar();
        return partidoRepository.save(contextoPartido.getPartido());
    }

    @Override
    public Partido cancelarPartido(Long id) {
        Partido partido = partidoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Partido no encontrado: " + id));

        Jugador jugador = jugadorService.getJugadorById(partido.getCreador().getId());
        User user = userService.getCurrentUser();
        jugadorService.validarUsuario(jugador, user);

        contextoPartido.iniciarContexto(partido);
        contextoPartido.cancelar();
        return partidoRepository.save(contextoPartido.getPartido());
    }

    @Override
    public List<Partido> filtrar(Long jugadorID, TipoFiltro tipoDeFiltro) {
        Jugador jugador = jugadorService.getJugadorById(jugadorID);

        StrategyFiltrarPartido strategy;
        return switch (tipoDeFiltro) {
            case HISTORIAL -> {
                strategy = new FiltrarPorHistorial();
                yield strategy.filtrar(jugador, partidoRepository);
            }
            case NIVEL -> {
                strategy = new FiltrarPorNivel();
                yield strategy.filtrar(jugador, partidoRepository);
            }
            case UBICACION -> {
                strategy = new FiltrarPorUbicacion();
                yield strategy.filtrar(jugador, partidoRepository);
            }
        };
    }

}
