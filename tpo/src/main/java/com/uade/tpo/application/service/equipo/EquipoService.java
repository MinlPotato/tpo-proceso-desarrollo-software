package com.uade.tpo.application.service.equipo;

import com.uade.tpo.application.dto.EquipoCreateDTO;
import com.uade.tpo.application.dto.EquipoDTO;
import com.uade.tpo.application.entity.Equipo;
import com.uade.tpo.application.entity.Jugador;
import com.uade.tpo.application.entity.Partido;
import com.uade.tpo.application.repository.EquipoRepository;
import com.uade.tpo.application.repository.JugadorRepository;

import com.uade.tpo.application.service.partido.IPartidoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EquipoService implements IEquipoService {
    @Autowired
    private IPartidoService partidoService;
    @Autowired
    private EquipoRepository equipoRepository;

    @Override
    public List<Equipo> getEquipos() {
        return equipoRepository.findAll();
    }

    @Override
    public Equipo getEquipoById(Long id) {
        return equipoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Equipo no encontrado con ID: " + id));
    }

    @Override
    @Transactional
    public Equipo createEquipo(EquipoCreateDTO dto) {
        try {
            if (dto == null) {
                throw new IllegalArgumentException("El DTO de creación de equipo no puede ser nulo");
            }
            if (dto.getNombre() == null || dto.getIdPartido() == null) {
                throw new IllegalArgumentException("El nombre del equipo y el ID del partido no pueden ser nulos");
            }

            Partido partido = partidoService.getPartidoById(dto.getIdPartido());

            // Crear y poblar la entidad Equipo
            Equipo equipo = new Equipo();
            equipo.setNombre(dto.getNombre());
            equipo.setPartido(partido);

            equipoRepository.save(equipo);
            return equipo;

        } catch (Exception e) {
            // TODO: handle exception
            throw new UnsupportedOperationException("Error al crear el equipo: " + e.getMessage(), e);
        }

    }

    @Override
    public Equipo updateEquipo(Long id, EquipoDTO requestBody) {
        // Buscar el equipo existente
        Equipo equipo = equipoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Equipo no encontrado con ID: " + id));
        // Actualizar el nombre
        equipo.setNombre(requestBody.getNombre());
        // Guardar y devolver el DTO actualizado
        return equipoRepository.save(equipo);
    }


    @Override
    public void deleteEquipo(Long id) {
        if (!equipoRepository.existsById(id)) {
            throw new RuntimeException("Equipo no encontrado con ID: " + id);
        }
        equipoRepository.deleteById(id);
    }

    public void unirseEquipo(Equipo equipo, Jugador jugador) {
        List<Jugador> jugadores = equipo.getJugadores();

        if (!jugadores.contains(jugador)) {
            jugadores.add(jugador);
            equipoRepository.save(equipo);
        } else {
            throw new IllegalArgumentException("El jugador ya se encuentra en el equipo.");
        }

    }

    @Override
    public boolean abandonarEquipo(Equipo equipo, Jugador jugador) {
        List<Jugador> jugadores = equipo.getJugadores();

        if (jugadores.contains(jugador)) {
            jugadores.remove(jugador);
            equipoRepository.save(equipo);
        } else {
            throw new IllegalArgumentException("El jugador no se encuentra en el equipo.");
        }

        return true;
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
