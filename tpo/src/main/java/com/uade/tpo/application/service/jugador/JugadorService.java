package com.uade.tpo.application.service.jugador;

import com.uade.tpo.application.dto.JugadorCreateDTO;
import com.uade.tpo.application.dto.JugadorDTO;
import com.uade.tpo.application.dto.NivelCreateDTO;
import com.uade.tpo.application.dto.NivelDTO;
import com.uade.tpo.application.entity.Nivel;
import com.uade.tpo.application.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

import com.uade.tpo.application.entity.Jugador;
import com.uade.tpo.application.repository.JugadorRepository;

@Service
public class JugadorService implements IJugadorService {

    @Autowired
    private JugadorRepository jugadorRepository;


    @Override
    public List<JugadorDTO> getJugadores() {
        return jugadorRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public JugadorDTO getJugadorById(Long id) {
        Jugador jugador = jugadorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Jugador not found with id " + id));
        return toDTO(jugador);
    }

    @Override
    public JugadorDTO createJugador(JugadorCreateDTO requestBody) {

        // Validar datos

        Jugador jugador = new Jugador(requestBody);
        return toDTO(jugadorRepository.save(jugador));
    }

    @Override
    public JugadorDTO updateJugador(Long id, JugadorCreateDTO requestBody) {
        Jugador jugador = jugadorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Jugador not found with id " + id));

        jugador.setNombre(requestBody.getNombre());
        jugador.setEmail(requestBody.getEmail());
        jugador.setUbicacion(requestBody.getUbicacion());
        jugador.setFormaNotificar(requestBody.getFormaNotificar());

        return toDTO(jugadorRepository.save(jugador));
    }


    @Override
    public void eliminarJugador(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID must be a positive number");
        }

        if (!jugadorRepository.existsById(id)) {
            throw new IllegalArgumentException("Deporte not found with id: " + id);
        }

        jugadorRepository.deleteById(id);
    }

    private JugadorDTO toDTO(Jugador jugador) {


        List<NivelDTO> nivelDTOS = jugador.getNiveles()
                .stream()
                .map(nivel -> new NivelDTO(nivel.getId(), nivel.getJugador().getId(), nivel.getDeporte().getId(), nivel.getNivel(), nivel.getFavorito()))
                .toList();

        return new JugadorDTO(
                jugador.getId(),
                jugador.getNombre(),
                jugador.getEmail(),
                jugador.getUbicacion(),
                nivelDTOS,
                jugador.getFormaNotificar()
        );
    }
}
