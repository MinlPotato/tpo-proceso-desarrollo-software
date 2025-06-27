package com.uade.tpo.application.service.jugador;


import com.uade.tpo.application.dto.JugadorCreateDTO;
import com.uade.tpo.application.dto.JugadorDTO;
import com.uade.tpo.application.dto.NivelCreateDTO;
import com.uade.tpo.application.entity.Jugador;

import java.util.List;

public interface IJugadorService {

    List<Jugador> getJugadores();

    Jugador getJugadorById(Long id);

    Jugador createJugador(JugadorCreateDTO requestBody);

    Jugador updateJugador(Long id, JugadorCreateDTO requestBody);

    //JugadorDTO agregarDeporte(NivelCreateDTO requestBody);

    void eliminarJugador(Long id);

}
