package com.uade.tpo.application.service.partido;

import com.uade.tpo.application.dto.EstadoDTO;
import com.uade.tpo.application.dto.PartidoCreateDTO;
import com.uade.tpo.application.dto.PartidoDTO;
import com.uade.tpo.application.entity.Partido;
import com.uade.tpo.application.enums.EnumEstadoPartido;
import com.uade.tpo.application.repository.DeporteRepository;
import com.uade.tpo.application.repository.EquipoRepository;
import com.uade.tpo.application.repository.JugadorRepository;
import com.uade.tpo.application.repository.PartidoRepository;
import com.uade.tpo.application.service.contexto.IContextoPartido;
import com.uade.tpo.application.service.contexto.IObservador;
import com.uade.tpo.application.service.factory.FactoryEstadoPartido;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PartidoService implements IPartidoService {

    @Autowired private PartidoRepository partidoRepository;
    @Autowired private JugadorRepository jugadorRepository;
    @Autowired private DeporteRepository deporteRepository;
    @Autowired private EquipoRepository equipoRepository;

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
        p.setMinJugadoresNecesarios(dto.getMinJugadoresNecesarios());

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

    private PartidoDTO toDTO(Partido p) {
        EstadoDTO est = p.getEstado() != null
                ? new EstadoDTO(
                p.getEstado().getNombre(),
                p.getEstado().getDescripcion(),
                p.getEstado().getMensaje())
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
                p.getTipoAdmision(),
                est
        );
    }
}
