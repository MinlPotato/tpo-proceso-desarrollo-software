package com.uade.tpo.application.service.deporte;
import com.uade.tpo.application.dto.DeporteCreateDTO;
import com.uade.tpo.application.dto.DeporteDTO;
import com.uade.tpo.application.entity.Deporte;

import java.util.List;
import java.util.Optional;


public interface IDeporteService {
    public List<Deporte> getDeportes();

    public Deporte getDeporteById(Long id);

    public Deporte createDeporte(DeporteCreateDTO deporte);

    public Deporte updateDeporte(Long id, DeporteCreateDTO deporte);

    public void deleteDeporte(Long id);

}
