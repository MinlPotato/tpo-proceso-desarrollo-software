package com.uade.tpo.application.deporte.controller;

import com.uade.tpo.application.deporte.dto.DeporteCreateDTO;
import com.uade.tpo.application.deporte.dto.DeporteDTO;
import com.uade.tpo.application.deporte.entity.Deporte;
import com.uade.tpo.application.deporte.service.DeporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/deportes")
public class DeporteController {

    @Autowired
    private DeporteService deporteService;

    @GetMapping
    public List<DeporteDTO> getAllDeportes() {
        try {
            return deporteService.getDeportes();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of(); // Devuelve una lista vacía en caso de error
        }
    }

    @GetMapping("/{id}")
    public DeporteDTO getDeporteById(@PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("ID must be a positive number");
            }
            else{
                return deporteService.getDeporteById(id)
                        .orElseThrow(() -> new RuntimeException("Deporte not found with id " + id));

            }
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Devuelve una lista vacía en caso de error
        }

    }
    @PostMapping("/create")
    public Deporte createDeporte(@RequestBody DeporteCreateDTO deporte) {
        try {
            if (deporte == null || deporte.getNombre() == null || deporte.getNombre().isEmpty()) {
                throw new IllegalArgumentException("Deporte name cannot be null or empty");
            }
            return deporteService.createDeporte(deporte);
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Devuelve null en caso de error
        }
    }
    @PutMapping("/{id}")
    public DeporteDTO updateDeporte(@PathVariable Long id, @RequestBody DeporteDTO deporte) {
        try {
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("ID must be a positive number");
            }
            if (deporte == null || deporte.getNombre() == null || deporte.getNombre().isEmpty()) {
                throw new IllegalArgumentException("Deporte name cannot be null or empty");
            }
            DeporteDTO existingDeporte = deporteService.getDeporteById(id)
                    .orElseThrow(() -> new RuntimeException("Deporte not found with id " + id));
            existingDeporte.setNombre(deporte.getNombre());
            return deporteService.updateDeporte(existingDeporte);
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Devuelve null en caso de error
        }
    }
    public void deleteDeporte(@PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("ID must be a positive number");
            }
            deporteService.deleteDeporte(id);
        } catch (Exception e) {
            e.printStackTrace();
     
        }
    }
}
