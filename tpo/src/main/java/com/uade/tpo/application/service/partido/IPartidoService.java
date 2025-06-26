package com.uade.tpo.application.service.partido;

import com.uade.tpo.application.dto.AgregarJugadorDTO;
import com.uade.tpo.application.dto.PartidoCreateDTO;
import com.uade.tpo.application.dto.PartidoDTO;
import com.uade.tpo.application.entity.Partido;
import com.uade.tpo.application.service.contexto.IObservador;

import java.util.List;

public interface IPartidoService {
    List<PartidoDTO> getPartidos();

    PartidoDTO getPartidoById(Long id);

    PartidoDTO createPartido(PartidoCreateDTO dto);

    PartidoDTO updatePartido(Long id, PartidoDTO dto);

    void deletePartido(Long id);

    PartidoDTO agregarJugador(Long partidoId, AgregarJugadorDTO agregarJugadorDTO);

    void desuscribirObservador(Long partidoId, AgregarJugadorDTO agregarJugadorDTO);

    PartidoDTO confirmarPartido(Long id);

    void iniciarPartido(Partido partido);

    PartidoDTO cancelarPartido(Long id);

    //filtros de partidos para emparejar jugadores
    List<PartidoDTO> filtrar(Long jugadorId, String tipofiltro);

}
