package com.uade.tpo.application.service.equipo;

import java.util.List;

import com.uade.tpo.application.dto.EquipoDTO;
import com.uade.tpo.application.dto.EquipoCreateDTO;
import com.uade.tpo.application.entity.Equipo;
import com.uade.tpo.application.entity.Jugador;
import com.uade.tpo.application.entity.Partido;

public interface IEquipoService {
    List<Equipo> getEquipos();

    Equipo getEquipoById(Long id);

    Equipo createEquipo(Partido partido, EquipoCreateDTO requestBody);

    Equipo updateEquipo(Long id, EquipoDTO requestBody);

    void deleteEquipo(Long id);

    void unirseEquipo(Equipo equipo, Jugador jugador);

    boolean abandonarEquipo(Equipo equipo, Jugador jugador);
}
