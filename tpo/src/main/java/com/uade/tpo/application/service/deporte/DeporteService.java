package com.uade.tpo.application.service.deporte;

import com.uade.tpo.application.dto.DeporteCreateDTO;
import com.uade.tpo.application.dto.DeporteDTO;
import com.uade.tpo.application.entity.Deporte;
import com.uade.tpo.application.repository.DeporteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public DeporteDTO getDeporteById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID must be a positive number");
        }

        Deporte deporte = deporteRepository.findById(id).orElseThrow(() -> new RuntimeException("Deporte not found with id " + id));
        return toDTO(deporte);

    }

    public DeporteDTO createDeporte(DeporteCreateDTO deporteCreateDTO) {

        if (deporteCreateDTO == null || deporteCreateDTO.getNombre() == null || deporteCreateDTO.getNombre().isEmpty()) {
            throw new IllegalArgumentException("Deporte name cannot be null or empty");
        }
        if (deporteRepository.existsByNombre(deporteCreateDTO.getNombre())) {
            throw new IllegalArgumentException("Deporte with name " + deporteCreateDTO.getNombre() + " already exists");
        }
        Deporte deporte = new Deporte();
        deporte.setNombre(deporteCreateDTO.getNombre());
        deporte.setDescripcion(deporte.getDescripcion());

        Deporte savedDeporte = deporteRepository.save(deporte);

        return toDTO(savedDeporte);

    }

    public void deleteDeporte(Long id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID must be a positive number");
        }

        if (!deporteRepository.existsById(id)) {
            throw new IllegalArgumentException("Deporte not found with id: " + id);
        }

        deporteRepository.deleteById(id);
    }

    public DeporteDTO updateDeporte(Long id, DeporteDTO deporteDTO) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID must be a positive number");
        }

        if (deporteDTO == null || deporteDTO.getNombre() == null || deporteDTO.getNombre().isEmpty()) {
            throw new IllegalArgumentException("Deporte name cannot be null or empty");
        }

        Deporte deporte = deporteRepository.findById(id).orElseThrow(() -> new RuntimeException("Deporte not found with id " + id));;
        deporte.setNombre(deporteDTO.getNombre());
        deporte.setDescripcion(deporteDTO.getDescripcion());

        return toDTO(deporteRepository.save(deporte));

    }

    private DeporteDTO toDTO(Deporte deporte) {
        return new DeporteDTO(deporte.getId(), deporte.getNombre(), deporte.getDescripcion());
    }

}
