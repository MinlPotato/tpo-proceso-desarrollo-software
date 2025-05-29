package com.uade.tpo.application.deporte.service;

import com.uade.tpo.application.deporte.dto.DeporteCreateDTO;
import com.uade.tpo.application.deporte.dto.DeporteDTO;
import java.util.List;
import java.util.Optional;


public interface IDeporteService {
  public List<DeporteDTO> getDeportes();  
  public Optional<DeporteDTO> getDeporteById(Long id);
    public DeporteDTO createDeporte(DeporteCreateDTO deporte);
    public DeporteDTO updateDeporte( DeporteDTO deporte);
    public void deleteDeporte(Long id);

}
