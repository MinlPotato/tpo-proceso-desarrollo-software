package com.uade.tpo.application.service.nivel;
import com.uade.tpo.application.dto.NivelCreateDTO;
import com.uade.tpo.application.dto.NivelDTO;
import com.uade.tpo.application.entity.Deporte;
import com.uade.tpo.application.entity.Jugador;
import com.uade.tpo.application.entity.Nivel;

import java.util.List;


public interface INivelService {
    public Nivel getNivelById(Long id);

    public Nivel createNivel(Long jugadorId, NivelCreateDTO nivelCreateDTO);

    public Nivel updateNivel(Jugador jugador, Deporte deporte, NivelCreateDTO requestBody);

    public void deleteNivel(Long jugadorId, Long nivelId);

    public List<Jugador> buscarJugadoresQueTienenComoFavorito(Deporte deporte);
}