package com.uade.tpo.application.service.partido;

import com.uade.tpo.application.dto.EstadoDTO;
import com.uade.tpo.application.dto.JugadorDTO;
import com.uade.tpo.application.dto.NivelDTO;
import com.uade.tpo.application.dto.PartidoCreateDTO;
import com.uade.tpo.application.dto.PartidoDTO;
import com.uade.tpo.application.entity.Jugador;
import com.uade.tpo.application.entity.Partido;
import com.uade.tpo.application.repository.DeporteRepository;
import com.uade.tpo.application.repository.EquipoRepository;
import com.uade.tpo.application.repository.JugadorRepository;
import com.uade.tpo.application.repository.PartidoRepository;
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

    @Autowired
    private PartidoRepository partidoRepository;
    @Autowired
    private JugadorRepository jugadorRepository;
    @Autowired
    private DeporteRepository deporteRepository;
    @Autowired
    private EquipoRepository equipoRepository;

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
        // Inicializa estado
        p.setEstado(new PartidoArmado());
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
    public boolean avanzarPartido(Long id) {
        Partido p = partidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Partido no encontrado: " + id));
        var siguiente = p.getEstado().avanzar(p);
        p.setEstado(siguiente);
        partidoRepository.save(p);
        return true;
    }

    @Override
    public boolean cancelarPartido(Long id) {
        Partido p = partidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Partido no encontrado: " + id));
        var cancelado = p.getEstado().cancelar(p);
        p.setEstado(cancelado);
        partidoRepository.save(p);
        return true;
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
                return strategy.filtrar(jugadorDTO, partidoRepository);
            case "nivel":
                strategy = new FiltrarPorNivel();
                return strategy.filtrar(jugadorDTO, partidoRepository);
            case "ubicacion":
                strategy = new FiltrarPorUbicacion();
                return strategy.filtrar(jugadorDTO, partidoRepository);
            default:
                throw new IllegalArgumentException("Tipo de filtro no soportado: " + tipoDeFiltro);
        }
    }

    private PartidoDTO toDTO(Partido p) {
        EstadoDTO est = p.getEstado() != null
                ? new EstadoDTO(p.getEstado().getNombre(), p.getEstado().getDescripcion(), p.getEstado().getMensaje())
                : null;
        int cntEq = p.getEquipos() != null ? p.getEquipos().size() : 0;
        int cntJug = cntEq > 0 ? p.getEquipos().get(0).getJugadores().size() : 0;
        return new PartidoDTO(
                p.getId(),
                p.getCreador().getId(),
                p.getDeporte().getId(),
                p.getDuracion(),
                p.getHorario(),
                p.getUbicacion(),
                cntEq,
                cntJug,
                est
        );
    }

}
