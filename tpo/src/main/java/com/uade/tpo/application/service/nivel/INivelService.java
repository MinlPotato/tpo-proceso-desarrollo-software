package com.uade.tpo.application.service.nivel;
import com.uade.tpo.application.dto.NivelCreateDTO;
import com.uade.tpo.application.dto.NivelDTO;
import com.uade.tpo.application.entity.Deporte;
import com.uade.tpo.application.entity.Jugador;


public interface INivelService {
    public NivelDTO getNivelById(Long id);

    public NivelDTO createNivel(Long jugadorId, NivelCreateDTO nivelCreateDTO);

    public NivelDTO updateNivel(Jugador jugador, Deporte deporte, NivelCreateDTO requestBody);

    public void deleteNivel(Long jugadorId, Long nivelId);
}