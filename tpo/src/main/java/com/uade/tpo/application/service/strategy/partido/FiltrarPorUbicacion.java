package com.uade.tpo.application.service.strategy.partido;

import java.util.List;

import com.uade.tpo.application.dto.JugadorDTO;
import com.uade.tpo.application.entity.Jugador;
import com.uade.tpo.application.entity.Partido;
import com.uade.tpo.application.enums.EnumEstadoPartido;
import com.uade.tpo.application.repository.PartidoRepository;

public class FiltrarPorUbicacion implements StrategyFiltrarPartido {

    @Override
    public List<Partido> filtrar(Jugador jugador, PartidoRepository PartidoRepository) {

        try {
            // Obtener la ubicación del jugador
            String ubicacionJugador = jugador.getUbicacion();

            // Filtrar los partidos por la ubicación del jugador
            List<Partido> partidosFiltrados = PartidoRepository.findByUbicacion(ubicacionJugador)
                .stream()
                .filter(partido -> partido.getEstado().equals(EnumEstadoPartido.NECESITA_JUGADORES))
                .toList();

            if(partidosFiltrados.isEmpty()) {
                return List.of(); // Retornar una lista vacía si no hay partidos en la ubicación
            }

            return partidosFiltrados;

        } catch (Exception e) {
            throw new RuntimeException("Error al filtrar partidos por ubicación: " + e.getMessage(), e);
        }
    }
} 
