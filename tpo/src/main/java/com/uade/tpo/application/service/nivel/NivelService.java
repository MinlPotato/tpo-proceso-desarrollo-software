package com.uade.tpo.application.service.nivel;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;

import com.uade.tpo.application.dto.NivelCreateDTO;
import com.uade.tpo.application.dto.NivelDTO;
import com.uade.tpo.application.entity.Nivel;
import com.uade.tpo.application.enums.NivelDeporte;
import com.uade.tpo.application.repository.DeporteRepository;
import com.uade.tpo.application.repository.JugadorRepository;
import com.uade.tpo.application.repository.NivelRepository;
import com.uade.tpo.application.entity.Deporte;
import com.uade.tpo.application.entity.Jugador;



public class NivelService implements INivelService {

    // Autowired repository or any other dependencies can be added here
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
            nivel.getNivel().name(), // o nivel.getNivel().toString()
            nivel.getFavorito()
        );
    }
public NivelDTO createNivel(NivelCreateDTO nivelCreateDTO) {
    if (nivelCreateDTO == null) {
        throw new IllegalArgumentException("NivelCreateDTO cannot be null");
    }

    if (nivelCreateDTO.getIdJugador() == null || nivelCreateDTO.getIdDeporte() == null || nivelCreateDTO.getNivel() == null) {
        throw new IllegalArgumentException("Jugador ID, Deporte ID, and Nivel cannot be null");
    }

    if (nivelCreateDTO.getIdJugador() <= 0 || nivelCreateDTO.getIdDeporte() <= 0) {
        throw new IllegalArgumentException("Jugador ID and Deporte ID must be positive numbers");
    }

    if (nivelRepository.exists(nivelCreateDTO.getIdJugador(), nivelCreateDTO.getIdDeporte())) {
        throw new RuntimeException("Nivel already exists for the given Jugador and Deporte");
    }

    Deporte deporte = deporteRepository.findById(nivelCreateDTO.getIdDeporte())
            .orElseThrow(() -> new RuntimeException("Deporte not found with id " + nivelCreateDTO.getIdDeporte()));

    Jugador jugador = jugadorRepository.findById(nivelCreateDTO.getIdJugador())
            .orElseThrow(() -> new RuntimeException("Jugador not found with id " + nivelCreateDTO.getIdJugador()));

    NivelDeporte nivelEnum;
    try {
        nivelEnum = NivelDeporte.valueOf(nivelCreateDTO.getNivel().toUpperCase());
    } catch (IllegalArgumentException e) {
        throw new RuntimeException("Nivel inválido: " + nivelCreateDTO.getNivel());
    }

    Nivel nuevoNivel = new Nivel(deporte, jugador, nivelEnum, nivelCreateDTO.isFavorito());
    Nivel savedNivel = nivelRepository.save(nuevoNivel);

    return new NivelDTO(
        savedNivel.getId(),
        savedNivel.getJugador().getId(),
        savedNivel.getDeporte().getId(),
        savedNivel.getNivel().name(),
        savedNivel.getFavorito()
    );
}

    public NivelDTO updateNivel(Long id, NivelDTO nivelDTO) {
        // Implementation to update an existing Nivel
        return null; // Placeholder return statement
    }


    public boolean deleteNivel(Long id) {
        // Implementation to delete a Nivel by ID
        return false;
    }
    
}
