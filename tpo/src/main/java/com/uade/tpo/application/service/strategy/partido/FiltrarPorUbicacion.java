package com.uade.tpo.application.service.strategy.partido;

import java.util.List;

import com.uade.tpo.application.dto.JugadorDTO;
import com.uade.tpo.application.dto.PartidoDTO;
import com.uade.tpo.application.entity.Partido;
import com.uade.tpo.application.repository.PartidoRepository;

public class FiltrarPorUbicacion implements StrategyFiltrarPartido {

    @Override
    public List<PartidoDTO> filtrar(JugadorDTO jugador, PartidoRepository PartidoRepository) {

        try {
            // Obtener la ubicación del jugador
            String ubicacionJugador = jugador.getUbicacion();

            // Filtrar los partidos por la ubicación del jugador
            List<Partido> partidosFiltrados = PartidoRepository.findByUbicacion(ubicacionJugador);
            if(partidosFiltrados == null || partidosFiltrados.isEmpty()) {
                return List.of(); // Retornar una lista vacía si no hay partidos en la ubicación
            }
            return partidosFiltrados.stream()

                    .filter(partido -> partido.getEstado().getNombre().equals("Necesita Jugadores"))
                    .map(partido -> new PartidoDTO)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Error al filtrar partidos por ubicación: " + e.getMessage(), e);
        }
    
       
    }



} 
