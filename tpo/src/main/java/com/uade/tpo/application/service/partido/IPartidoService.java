package com.uade.tpo.application.service.partido;

import com.uade.tpo.application.dto.PartidoCreateDTO;
import com.uade.tpo.application.dto.PartidoDTO;
import com.uade.tpo.application.service.contexto.IObservador;

import java.util.List;

public interface IPartidoService {
    List<PartidoDTO> getPartidos();
    PartidoDTO getPartidoById(Long id);
    PartidoDTO createPartido(PartidoCreateDTO dto);
    PartidoDTO updatePartido(Long id, PartidoDTO dto);

    /** Suscribe un observador para recibir notificaciones de cambio de estado */
    void suscribirObservador(Long partidoId, IObservador observador);

    /** Desuscribe un observador previamente suscrito */
    void desuscribirObservador(Long partidoId, IObservador observador);

    void deletePartido(Long id);
    boolean avanzarPartido(Long id);
    boolean cancelarPartido(Long id);
}