package com.uade.tpo.application.service.strategy.partido;

import java.util.List;

import com.uade.tpo.application.dto.JugadorDTO;
import com.uade.tpo.application.dto.PartidoDTO;

import com.uade.tpo.application.repository.PartidoRepository;


public interface StrategyFiltrarPartido {
    
    List<PartidoDTO> filtrar(JugadorDTO jugador,PartidoRepository partidoRepository);
}