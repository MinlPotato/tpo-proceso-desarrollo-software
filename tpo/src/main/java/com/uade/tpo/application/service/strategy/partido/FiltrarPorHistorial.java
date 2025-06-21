package com.uade.tpo.application.service.strategy.partido;

import java.util.List;

import com.uade.tpo.application.dto.JugadorDTO;
import com.uade.tpo.application.dto.PartidoDTO;
import com.uade.tpo.application.entity.Partido;
import com.uade.tpo.application.repository.PartidoRepository;

public class FiltrarPorHistorial implements StrategyFiltrarPartido {

    @Override
    public List<PartidoDTO> filtrar(JugadorDTO jugador, PartidoRepository PartidoRepository) {
        try {
            // Obtener el historial de partidos del jugador
            List<Partido> historialPartidos = PartidoRepository.findByJugadorId(jugador.getId());

            // Filtrar los partidos por el historial del jugador
            if (historialPartidos == null || historialPartidos.isEmpty()) {
                return List.of(); // Retornar una lista vacía si no hay partidos en el historial
            }
            return historialPartidos.stream()
                    .map(partido -> new PartidoDTO(partido.getId(), partido.getDuracion(), partido.getHorario(),
                            partido.getUbicacion(), partido.getCreador().getId()))
                    .toList();
        } catch (Exception e) {
            // TODO: handle exception
        }
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'filtrar'");
    }

    
}