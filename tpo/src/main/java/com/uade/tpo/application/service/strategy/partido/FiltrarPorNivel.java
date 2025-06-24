package com.uade.tpo.application.service.strategy.partido;

import java.util.List;

import com.uade.tpo.application.dto.JugadorDTO;
import com.uade.tpo.application.dto.NivelDTO;
import com.uade.tpo.application.entity.Partido;
import com.uade.tpo.application.enums.EnumEstadoPartido;
import com.uade.tpo.application.repository.PartidoRepository;

public class FiltrarPorNivel implements StrategyFiltrarPartido {

    @Override
    public List<Partido> filtrar(JugadorDTO jugador, PartidoRepository partidoRepository) {
        try {
            List<Partido> partidos = null;
            List<NivelDTO> niveles = jugador.getNiveles();
            for (NivelDTO nivelDTO : niveles) {
                //filtro por deporte
                List<Partido> partidosFiltrados = partidoRepository.findByDeporteId(
                        nivelDTO.getIdDeporte()
                );

                if (partidosFiltrados != null && !partidosFiltrados.isEmpty()) {
                    
                    if (partidos == null) {
                        partidos = partidosFiltrados;
                    } else {
                        partidos.addAll(partidosFiltrados);
                    }
                }
            }

            if (partidos == null || partidos.isEmpty()) {
                return List.of(); // Retornar una lista vacía si no hay partidos en el nivel
            }
            return partidos.stream()
                    .filter(partido ->partido.getEstado().equals(EnumEstadoPartido.NECESITA_JUGADORES))
                    .toList();

        } catch (Exception e) {
            throw new RuntimeException("Error al filtrar partidos por nivel", e);
        }
     
    }
}