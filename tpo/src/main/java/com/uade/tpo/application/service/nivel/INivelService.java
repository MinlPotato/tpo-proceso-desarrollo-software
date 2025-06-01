package com.uade.tpo.application.service.nivel;
import com.uade.tpo.application.dto.NivelCreateDTO;
import com.uade.tpo.application.dto.NivelDTO;


public interface INivelService {
    public NivelDTO getNivelById(Long id);

    public NivelDTO createNivel(NivelCreateDTO nivelCreateDTO);

    public NivelDTO updateNivel(Long id, NivelDTO nivelDTO);

    public boolean deleteNivel(Long id);
}