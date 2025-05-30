package com.uade.tpo.application.service.equipo;

import com.uade.tpo.application.dto.DeporteCreateDTO;
import com.uade.tpo.application.dto.DeporteDTO;
import com.uade.tpo.application.entity.Deporte;
import com.uade.tpo.application.repository.DeporteRepository;
import com.uade.tpo.application.service.deporte.IDeporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public DeporteDTO createDeporte(DeporteCreateDTO deporteCreateDTO) {

        Deporte deporte = new Deporte();
        deporte.setNombre(deporteCreateDTO.getNombre());
        deporte.setDescripcion(deporte.getDescripcion());

        Deporte savedDeporte = deporteRepository.save(deporte);

        return toDTO(savedDeporte);

    }

    public void deleteDeporte(Long id) {
        deporteRepository.deleteById(id);
    }

    public DeporteDTO updateDeporte(Long id, DeporteDTO deporte) {


        Deporte dep = deporteRepository.save(deporte);
        return toDTO(dep);

    }

    private DeporteDTO toDTO(Deporte deporte) {
        return new DeporteDTO(deporte.getId(), deporte.getNombre(), deporte.getDescripcion());
    }

}
