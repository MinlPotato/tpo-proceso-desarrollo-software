package com.uade.tpo.application.service.partido;

import com.uade.tpo.application.dto.PartidoCreateDTO;
import com.uade.tpo.application.dto.PartidoDTO;
import java.util.List;

public interface IPartidoService {
    List<PartidoDTO> getPartidos();
    PartidoDTO getPartidoById(Long id);
    PartidoDTO createPartido(PartidoCreateDTO dto);
    PartidoDTO updatePartido(Long id, PartidoDTO dto);
    //AgregarSuscribir
    void deletePartido(Long id);
    boolean avanzarPartido(Long id);
    boolean cancelarPartido(Long id);
}
