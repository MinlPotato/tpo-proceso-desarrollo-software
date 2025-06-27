package com.uade.tpo.application.service.partido;

import com.uade.tpo.application.dto.AgregarJugadorDTO;
import com.uade.tpo.application.dto.PartidoCreateDTO;
import com.uade.tpo.application.dto.PartidoDTO;
import com.uade.tpo.application.entity.Partido;
import com.uade.tpo.application.enums.TipoFiltro;

import java.util.List;

public interface IPartidoService {

    //CRUD
    List<Partido> getPartidos();

    Partido getPartidoById(Long id);

    Partido createPartido(PartidoCreateDTO dto);

    Partido updatePartido(Long id, PartidoDTO dto);

    void deletePartido(Long id);

    //State
    Partido agregarJugador(Long partidoId, AgregarJugadorDTO agregarJugadorDTO);

    Partido eliminarJugador(Long partidoId, AgregarJugadorDTO agregarJugadorDTO);

    Partido confirmarPartido(Long id);

    void iniciarPartido(Partido partido);

    Partido finalizarPartido(Long partidoId);

    Partido cancelarPartido(Long id);

    //filtros de partidos para emparejar jugadores
    List<Partido> filtrar(Long jugadorId, TipoFiltro tipofiltro);

}
