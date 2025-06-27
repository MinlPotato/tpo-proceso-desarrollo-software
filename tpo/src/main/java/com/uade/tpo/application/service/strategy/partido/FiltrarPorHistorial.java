package com.uade.tpo.application.service.strategy.partido;

import java.util.ArrayList;
import java.util.List;

import com.uade.tpo.application.dto.JugadorDTO;
import com.uade.tpo.application.entity.Deporte;
import com.uade.tpo.application.entity.Jugador;
import com.uade.tpo.application.entity.Partido;
import com.uade.tpo.application.enums.EnumEstadoPartido;
import com.uade.tpo.application.repository.PartidoRepository;

public class FiltrarPorHistorial implements StrategyFiltrarPartido {

    @Override
    public List<Partido> filtrar(Jugador jugador, PartidoRepository PartidoRepository) {
        try {
            // Obtener el historial de partidos del jugador
            List<Partido> historialPartidos = PartidoRepository.findByCreadorId(jugador.getId());

            List<Deporte> deportesJugados = new ArrayList<>();
            historialPartidos.forEach(partido -> {
                deportesJugados.add(partido.getDeporte());
            });

            List<Partido> filtroPartidos = new ArrayList<>();
            deportesJugados.forEach(deporte -> {
                filtroPartidos.addAll(PartidoRepository.findByDeporteId(deporte.getId()));
            });

            // Filtrar los partidos por el historial del jugador
            if (filtroPartidos.isEmpty()) {
                return List.of(); // Retornar una lista vacía si no hay partidos en el historial
            }
            return filtroPartidos.stream()
                .filter(partido ->partido.getEstado().equals(EnumEstadoPartido.NECESITA_JUGADORES))
                .toList();
        } catch (Exception e) {
            throw new RuntimeException("Error al filtrar partidos por historial: " + e.getMessage(), e);
        }
    }

    
}