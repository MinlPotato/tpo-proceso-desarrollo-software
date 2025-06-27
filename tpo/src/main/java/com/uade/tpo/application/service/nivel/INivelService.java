package com.uade.tpo.application.service.nivel;
import com.uade.tpo.application.dto.NivelCreateDTO;
import com.uade.tpo.application.entity.Deporte;
import com.uade.tpo.application.entity.Jugador;
import com.uade.tpo.application.entity.Nivel;

import java.util.List;


public interface INivelService {
    public Nivel getNivelById(Long id);

    public Nivel createNivel(Jugador jugador, NivelCreateDTO nivelCreateDTO);

    public Nivel updateNivel(Jugador jugador, Deporte deporte, NivelCreateDTO requestBody);

    public void deleteNivel(Jugador jugador, Long nivelId);

    public List<Jugador> buscarJugadoresComoFavorito(Deporte deporte);
}