package com.uade.tpo.application.service.equipo;

import com.uade.tpo.application.dto.EquipoCreateDTO;
import com.uade.tpo.application.dto.EquipoDTO;
import com.uade.tpo.application.entity.Equipo;
import com.uade.tpo.application.entity.Jugador;
import com.uade.tpo.application.entity.Partido;
import com.uade.tpo.application.repository.EquipoRepository;
import com.uade.tpo.application.repository.JugadorRepository;
import com.uade.tpo.application.repository.PartidoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EquipoService implements IEquipoService {
    @Autowired
    private PartidoRepository partidoRepository;
    @Autowired
    private JugadorRepository jugadorRepository;
    @Autowired
    private EquipoRepository equipoRepository;

    @Override
    public List<EquipoDTO> getEquipos() {
        return equipoRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    @Override
    public EquipoDTO getEquipoById(Long id) {
        Equipo equipo = equipoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Equipo no encontrado con ID: " + id));
        return convertToDTO(equipo);
    }

    @Override
    public EquipoDTO createEquipo(EquipoCreateDTO dto) {
        try {
            if (dto == null) {
            throw new IllegalArgumentException("El DTO de creación de equipo no puede ser nulo");
        }
        if (dto.getNombre() == null || dto.getIdPartido() == null) {
            throw new IllegalArgumentException("El nombre del equipo y el ID del partido no pueden ser nulos");
        }
        if (dto.getJugadoresIds() == null || dto.getJugadoresIds().isEmpty()) {
            throw new IllegalArgumentException("La lista de jugadores no puede estar vacía");
        }

        Partido partido = partidoRepository.findById(dto.getIdPartido())
        .orElseThrow(() -> new RuntimeException("Partido no encontrado con ID: " + dto.getIdPartido()));

        // Obtener los jugadores por ID
        List<Jugador> jugadores = jugadorRepository.findAllById(dto.getJugadoresIds());

        // Crear y poblar la entidad Equipo
        Equipo equipo = new Equipo();
        equipo.setNombre(dto.getNombre());
        equipo.setPartido(partido);
        equipo.setJugadores(jugadores);

        equipoRepository.save(equipo);
        return convertToDTO(equipo);

        } catch (Exception e) {
            // TODO: handle exception
            throw new UnsupportedOperationException("Error al crear el equipo: " + e.getMessage(), e);
        }
        
    }

    @Override
    public EquipoDTO updateEquipo(Long id, EquipoDTO requestBody) {
        // Buscar el equipo existente
        Equipo equipo = equipoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Equipo no encontrado con ID: " + id));

        // Actualizar el nombre
        equipo.setNombre(requestBody.getNombre());

        // Validar y actualizar el partido
        Long idPartido = requestBody.getIDPartido();
        Partido partido = partidoRepository.findById(idPartido)
            .orElseThrow(() -> new RuntimeException("Partido no encontrado con ID: " + idPartido));
        equipo.setPartido(partido);

        // Actualizar los jugadores
        List<Long> jugadoresIds = requestBody.getJugadoresIds();
        equipo.setJugadores(jugadorRepository.findAllById(jugadoresIds));

        // Guardar y devolver el DTO actualizado
        Equipo equipoActualizado = equipoRepository.save(equipo);
        return convertToDTO(equipoActualizado);
    }


    @Override
    public void deleteEquipo(Long id) {
        if (!equipoRepository.existsById(id)) {
            throw new RuntimeException("Equipo no encontrado con ID: " + id);
        }
        equipoRepository.deleteById(id);
    }
    public boolean unirseEquipo(Long idEquipo, Long idJugador) {
        Equipo equipo = equipoRepository.findById(idEquipo)
            .orElseThrow(() -> new RuntimeException("Equipo no encontrado con ID: " + idEquipo));
        Jugador jugador = jugadorRepository.findById(idJugador)
            .orElseThrow(() -> new RuntimeException("Jugador no encontrado con ID: " + idJugador));

        if (equipo.unirse(jugador)) {
            equipoRepository.save(equipo);
            return true;
        } else {

            return false;
        }
    }

    public boolean abandonarEquipo(Long idEquipo, Long idJugador) {
        Equipo equipo = equipoRepository.findById(idEquipo)
            .orElseThrow(() -> new RuntimeException("Equipo no encontrado con ID: " + idEquipo));
        Jugador jugador = jugadorRepository.findById(idJugador)
            .orElseThrow(() -> new RuntimeException("Jugador no encontrado con ID: " + idJugador));

        if (equipo.abandonar(jugador)) {
            equipoRepository.save(equipo);
            return true;
        } else {
            return false;
        }
    }

    private EquipoDTO convertToDTO(Equipo equipo) {
        List<Long> jugadoresIds = equipo.getJugadores().stream()
            .map(Jugador::getId)
            .collect(Collectors.toList());

        return new EquipoDTO(
            equipo.getId(),
            equipo.getNombre(),
            equipo.getPartido().getId(),
            jugadoresIds
        );
    }

}
