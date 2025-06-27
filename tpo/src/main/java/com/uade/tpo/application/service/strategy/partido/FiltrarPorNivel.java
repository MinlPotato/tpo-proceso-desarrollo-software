package com.uade.tpo.application.service.strategy.partido;

import java.util.ArrayList;
import java.util.List;

import com.uade.tpo.application.entity.Jugador;
import com.uade.tpo.application.entity.Nivel;
import com.uade.tpo.application.entity.Partido;
import com.uade.tpo.application.enums.EnumEstadoPartido;
import com.uade.tpo.application.repository.PartidoRepository;

public class FiltrarPorNivel implements StrategyFiltrarPartido {

    @Override
    public List<Partido> filtrar(Jugador jugador, PartidoRepository partidoRepository) {
        try {
            List<Partido> partidos = new ArrayList<>();
            List<Nivel> niveles = jugador.getNiveles();
            for (Nivel nivel : niveles) {
                //filtro por deporte
                List<Partido> partidosFiltrados = partidoRepository.findByDeporteId(
                    nivel.getDeporte().getId()
                );

                if (partidosFiltrados != null && !partidosFiltrados.isEmpty()) {
                    partidos.addAll(partidosFiltrados
                        .stream()
                        .filter(partido -> partido.getNivelesJugadores().contains(nivel.getNivel()))
                        .toList()
                    );
                }
            }

            if (partidos.isEmpty()) {
                return List.of(); // Retornar una lista vacía si no hay partidos en el nivel
            }

            return partidos.stream()
                .filter(partido -> EnumEstadoPartido.NECESITA_JUGADORES.equals(partido.getEstado()))
                .toList();

        } catch (Exception e) {
            throw new RuntimeException("Error al filtrar partidos por nivel", e);
        }

    }
}