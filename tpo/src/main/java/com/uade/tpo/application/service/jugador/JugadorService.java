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
    public List<Jugador> getJugadores() {
        return jugadorRepository.findAll();
    }

    @Override
    public Jugador getJugadorById(Long id) {
        return jugadorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Jugador not found with id " + id));
    }

    @Override
    public Jugador createJugador(JugadorCreateDTO requestBody) {

        // Validar datos

        Jugador jugador = new Jugador(requestBody);
        return jugadorRepository.save(jugador);
    }

    @Override
    public Jugador updateJugador(Long id, JugadorCreateDTO requestBody) {
        Jugador jugador = jugadorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Jugador not found with id " + id));

        jugador.setNombre(requestBody.getNombre());
        jugador.setEmail(requestBody.getEmail());
        jugador.setUbicacion(requestBody.getUbicacion());
        jugador.setFormaNotificar(requestBody.getFormaNotificar());

        return jugadorRepository.save(jugador);
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


}
