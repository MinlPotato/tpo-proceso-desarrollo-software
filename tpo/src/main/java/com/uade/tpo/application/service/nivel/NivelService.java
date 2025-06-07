package com.uade.tpo.application.service.nivel;

import com.uade.tpo.application.dto.DeporteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import com.uade.tpo.application.dto.NivelCreateDTO;
import com.uade.tpo.application.dto.NivelDTO;
import com.uade.tpo.application.entity.Nivel;
import com.uade.tpo.application.enums.NivelDeporte;
import com.uade.tpo.application.repository.DeporteRepository;
import com.uade.tpo.application.repository.JugadorRepository;
import com.uade.tpo.application.repository.NivelRepository;
import com.uade.tpo.application.entity.Deporte;
import com.uade.tpo.application.entity.Jugador;
import org.springframework.stereotype.Service;

@Service
public class NivelService implements INivelService {

    @Autowired
    private NivelRepository nivelRepository;
    @Autowired
    private DeporteRepository deporteRepository;
    @Autowired
    private JugadorRepository jugadorRepository;

    public NivelDTO getNivelById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID must be a positive number");
        }

        Nivel nivel = nivelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nivel not found with id " + id));

        return new NivelDTO(
                nivel.getId(),
                nivel.getJugador().getId(),
                nivel.getDeporte().getId(),
                nivel.getNivel(), // o nivel.getNivel().toString()
                nivel.getFavorito()
        );
    }

    public NivelDTO createNivel(Long jugadorId, NivelCreateDTO requestBody) {
        if (requestBody == null) {
            throw new IllegalArgumentException("NivelCreateDTO cannot be null");
        }

        if (jugadorId == null || requestBody.getIdDeporte() == null || requestBody.getNivel() == null) {
            throw new IllegalArgumentException("Jugador ID, Deporte ID, and Nivel cannot be null");
        }

        if (jugadorId <= 0 || requestBody.getIdDeporte() <= 0) {
            throw new IllegalArgumentException("Jugador ID and Deporte ID must be positive numbers");
        }

        Deporte deporte = deporteRepository.findById(requestBody.getIdDeporte())
                .orElseThrow(() -> new RuntimeException("Deporte not found with id " + requestBody.getIdDeporte()));

        Jugador jugador = jugadorRepository.findById(jugadorId)
                .orElseThrow(() -> new RuntimeException("Jugador not found with id " + jugadorId));


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
        Nivel savedNivel = nivelRepository.save(nuevoNivel);

        return new NivelDTO(
                savedNivel.getId(),
                savedNivel.getJugador().getId(),
                savedNivel.getDeporte().getId(),
                savedNivel.getNivel(),
                savedNivel.getFavorito()
        );
    }

    public NivelDTO updateNivel(Jugador jugador, Deporte deporte, NivelCreateDTO requestBody) {

        Nivel nivel = nivelRepository.findByJugadorAndDeporte(jugador, deporte);

        nivel.setNivel(requestBody.getNivel());
        nivel.setFavorito(requestBody.getFavorito());

        Nivel savedNivel = nivelRepository.save(nivel);

        return new NivelDTO(
                savedNivel.getId(),
                savedNivel.getJugador().getId(),
                savedNivel.getDeporte().getId(),
                savedNivel.getNivel(),
                savedNivel.getFavorito()
        );
    }

    @Override
    public void deleteNivel(Long jugadorId, Long nivelId) {
        if (jugadorId == null || nivelId == null) {
            throw new IllegalArgumentException("Jugador ID, Nivel ID cannot be null");
        }

        Nivel nivel = nivelRepository.findById(nivelId).orElseThrow(() -> new RuntimeException("Nivel not found with id " + nivelId));

        if (!nivel.getJugador().getId().equals(jugadorId)) {
            throw new IllegalArgumentException("Nivel ID given is not from the Jugador");
        }

        // Nivel nivel = nivelRepository.findById(id).orElseThrow(() -> new RuntimeException("Deporte not found with id " + id));

        nivelRepository.deleteById(nivelId);
    }

}
