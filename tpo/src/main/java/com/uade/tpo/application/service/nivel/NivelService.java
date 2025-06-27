package com.uade.tpo.application.service.nivel;

import com.uade.tpo.application.entity.User;
import com.uade.tpo.application.exception.ResourceNotFoundException;
import com.uade.tpo.application.service.deporte.IDeporteService;
import com.uade.tpo.application.service.jugador.IJugadorService;
import com.uade.tpo.application.service.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import com.uade.tpo.application.dto.NivelCreateDTO;
import com.uade.tpo.application.entity.Nivel;
import com.uade.tpo.application.enums.NivelDeporte;
import com.uade.tpo.application.repository.NivelRepository;
import com.uade.tpo.application.entity.Deporte;
import com.uade.tpo.application.entity.Jugador;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NivelService implements INivelService {

    @Autowired
    private NivelRepository nivelRepository;

    @Autowired
    private IDeporteService deporteService;
    @Autowired
    private IJugadorService jugadorService;
    @Autowired
    private UserService userService;

    @Override
    public Nivel getNivelById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID must be a positive number");
        }

        return nivelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nivel not found with id " + id));
    }

    @Override
    public Nivel createNivel(Jugador jugador, NivelCreateDTO requestBody) {

        if (requestBody == null) {
            throw new IllegalArgumentException("NivelCreateDTO cannot be null");
        }

        Deporte deporte = deporteService.getDeporteById(requestBody.getIdDeporte());
        User user = userService.getCurrentUser();
        jugadorService.validarUsuario(jugador, user);

        if (nivelRepository.existsByJugadorAndDeporte(jugador, deporte)) {
            return updateNivel(jugador, deporte, requestBody);
        }

        NivelDeporte nivelEnum;
        try {
            nivelEnum = NivelDeporte.valueOf(requestBody.getNivel().name());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Nivel inválido: " + requestBody.getNivel());
        }

        Nivel nuevoNivel = new Nivel(deporte, jugador, nivelEnum, requestBody.getFavorito());
        return nivelRepository.save(nuevoNivel);
    }

    @Override
    public Nivel updateNivel(Jugador jugador, Deporte deporte, NivelCreateDTO requestBody) {

        Nivel nivel = nivelRepository.findByJugadorAndDeporte(jugador, deporte);

        nivel.setNivel(requestBody.getNivel());
        nivel.setFavorito(requestBody.getFavorito());

        return nivelRepository.save(nivel);
    }

    @Override
    public void deleteNivel(Jugador jugador, Long nivelId) {

        Nivel nivel = nivelRepository.findById(nivelId).orElseThrow(() -> new RuntimeException("Nivel not found with id " + nivelId));
        User user = userService.getCurrentUser();
        jugadorService.validarUsuario(jugador, user);

        if (!nivel.getJugador().equals(jugador)) {
            throw new IllegalArgumentException("Nivel ID given is not from the Jugador");
        }

        nivelRepository.deleteById(nivelId);
    }

    @Override
    public List<Jugador> buscarJugadoresComoFavorito(Deporte deporte) {
        return nivelRepository.findJugadoresByDeporteFavorito(deporte);
    }
}
