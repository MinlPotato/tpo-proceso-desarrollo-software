package com.uade.tpo.application.service.deporte;

import com.uade.tpo.application.dto.DeporteCreateDTO;
import com.uade.tpo.application.dto.DeporteDTO;
import com.uade.tpo.application.entity.Deporte;
import com.uade.tpo.application.exception.ResourceNotFoundException;
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

        Deporte deporte = deporteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Deporte not found with id " + id));
        return toDTO(deporte);

    }

    public DeporteDTO createDeporte(DeporteCreateDTO requestBody) {

        if (requestBody == null || requestBody.getNombre() == null || requestBody.getNombre().isEmpty()) {
            throw new IllegalArgumentException("Deporte name cannot be null or empty");
        }
        if (deporteRepository.existsByNombre(requestBody.getNombre())) {
            throw new IllegalArgumentException("Deporte with name " + requestBody.getNombre() + " already exists");
        }
        Deporte deporte = new Deporte();
        deporte.setNombre(requestBody.getNombre());
        deporte.setDescripcion(requestBody.getDescripcion());

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

    public DeporteDTO updateDeporte(Long id, DeporteCreateDTO requestBody) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID must be a positive number");
        }

        if (requestBody == null || requestBody.getNombre() == null || requestBody.getNombre().isEmpty()) {
            throw new IllegalArgumentException("Deporte name cannot be null or empty");
        }

        Deporte deporte = deporteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Deporte not found with id " + id));;
        deporte.setNombre(requestBody.getNombre());
        deporte.setDescripcion(requestBody.getDescripcion());

        return toDTO(deporteRepository.save(deporte));

    }

    private DeporteDTO toDTO(Deporte deporte) {
        return new DeporteDTO(deporte.getId(), deporte.getNombre(), deporte.getDescripcion());
    }

}
