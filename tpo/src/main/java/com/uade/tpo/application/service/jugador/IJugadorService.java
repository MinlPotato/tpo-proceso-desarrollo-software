package com.uade.tpo.application.service.jugador;


import com.uade.tpo.application.dto.JugadorCreateDTO;
import com.uade.tpo.application.dto.JugadorDTO;
import com.uade.tpo.application.dto.NivelCreateDTO;

import java.util.List;

public interface IJugadorService {

    List<JugadorDTO> getJugadores();

    JugadorDTO getJugadorById(Long id);

    JugadorDTO createJugador(JugadorCreateDTO requestBody);

    JugadorDTO updateJugador(Long id, JugadorCreateDTO requestBody);

    //JugadorDTO agregarDeporte(NivelCreateDTO requestBody);

    void eliminarJugador(Long id);

}
