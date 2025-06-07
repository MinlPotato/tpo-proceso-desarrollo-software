package com.uade.tpo.application.service.deporte;
import com.uade.tpo.application.dto.DeporteCreateDTO;
import com.uade.tpo.application.dto.DeporteDTO;
import java.util.List;
import java.util.Optional;


public interface IDeporteService {
    public List<DeporteDTO> getDeportes();

    public DeporteDTO getDeporteById(Long id);

    public DeporteDTO createDeporte(DeporteCreateDTO deporte);

    public DeporteDTO updateDeporte(Long id, DeporteCreateDTO deporte);

    public void deleteDeporte(Long id);

}
