package com.uade.tpo.application.deporte.service;
import org.springframework.stereotype.Service;
import com.uade.tpo.application.deporte.repository.DeporteRepository;
import com.uade.tpo.application.deporte.dto.DeporteCreateDTO;
import com.uade.tpo.application.deporte.dto.DeporteDTO;
import com.uade.tpo.application.deporte.entity.Deporte;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;  
@Service
public class DeporteService implements IDeporteService {
    @Autowired
    private DeporteRepository deporteRepository;
    
    public List<DeporteDTO> getDeportes() {
    return deporteRepository.findAll()
            .stream()
            .map(this::toDTO)
            .toList();
    }

    public Optional<DeporteDTO> getDeporteById(Long id) {
            return deporteRepository.findById(id)
                    .map(this::toDTO);  // transforma el Deporte en DeporteDTO si existe
    }
    public Deporte createDeporte(DeporteCreateDTO deporte) {
     Deporte deport = new Deporte();

        deport.setNombre(deporte.getNombre());
        deport.setDescripcion(deporte.getDescripcion());


    return deporteRepository.save(deport);
    }
    public void deleteDeporte(Long id) {
        deporteRepository.deleteById(id);
    }

    public DeporteDTO updateDeporte( DeporteDTO deporte) {
        Deporte dep=         deporteRepository.save(deporte);
        return toDTO(dep);
       
    }
    private DeporteDTO toDTO(Deporte deporte) {
        return new DeporteDTO(deporte.getId(), deporte.getNombre(), deporte.getDescripcion());
    }




}
