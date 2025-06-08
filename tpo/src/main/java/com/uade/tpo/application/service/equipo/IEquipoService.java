package com.uade.tpo.application.service.equipo;
import java.util.List;

import com.uade.tpo.application.dto.EquipoDTO;  
import com.uade.tpo.application.dto.EquipoCreateDTO;

public interface IEquipoService {
    List<EquipoDTO> getEquipos();
    EquipoDTO getEquipoById(Long id);
    EquipoDTO createEquipo(EquipoCreateDTO requestBody);
   EquipoDTO updateEquipo(Long id, EquipoDTO requestBody);
   void deleteEquipo(Long id);
   boolean unirseEquipo(Long idEquipo, Long idJugador);
    boolean abandonarEquipo(Long idEquipo, Long idJugador);
}
