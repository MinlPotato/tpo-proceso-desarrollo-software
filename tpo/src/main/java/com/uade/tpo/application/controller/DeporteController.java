package com.uade.tpo.application.controller;

import com.uade.tpo.application.dto.DeporteCreateDTO;
import com.uade.tpo.application.dto.DeporteDTO;
import com.uade.tpo.application.entity.Deporte;
import com.uade.tpo.application.service.deporte.IDeporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/deportes")
public class DeporteController {

    @Autowired
    private IDeporteService deporteService;

    @GetMapping
    public ResponseEntity<List<DeporteDTO>> getDeportes() {
        return ResponseEntity.ok(deporteService.getDeportes()
            .stream()
            .map(this::toDTO)
            .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeporteDTO> getDeporteById(@PathVariable Long id) {
        Deporte deporte = deporteService.getDeporteById(id);
        return ResponseEntity.ok(toDTO(deporte));
    }

    @PostMapping
    public ResponseEntity<DeporteDTO> createDeporte(@RequestBody DeporteCreateDTO deporte) {
        Deporte creado = deporteService.createDeporte(deporte);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(creado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeporteDTO> updateDeporte(@PathVariable Long id, @RequestBody DeporteCreateDTO deporteCreateDTO) {
        Deporte deporte = deporteService.updateDeporte(id, deporteCreateDTO);
        return ResponseEntity.ok(toDTO(deporte));
    }

    @DeleteMapping("/{id}")
    public void deleteDeporte(@PathVariable Long id) {
        deporteService.deleteDeporte(id);
    }

    private DeporteDTO toDTO(Deporte deporte) {
        return new DeporteDTO(deporte.getId(), deporte.getNombre(), deporte.getDescripcion());
    }
}
