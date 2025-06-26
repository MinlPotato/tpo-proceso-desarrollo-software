package com.uade.tpo.application.service.strategy.partido;

import java.util.List;

import com.uade.tpo.application.dto.JugadorDTO;

import com.uade.tpo.application.entity.Jugador;
import com.uade.tpo.application.entity.Partido;
import com.uade.tpo.application.repository.PartidoRepository;


public interface StrategyFiltrarPartido {
    
    List<Partido> filtrar(Jugador jugador, PartidoRepository partidoRepository);
}