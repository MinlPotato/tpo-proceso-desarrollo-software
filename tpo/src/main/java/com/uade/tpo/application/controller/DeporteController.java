package com.uade.tpo.application.controller;

import com.uade.tpo.application.dto.DeporteCreateDTO;
import com.uade.tpo.application.dto.DeporteDTO;
import com.uade.tpo.application.service.deporte.IDeporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/deportes")
public class DeporteController {

    @Autowired
    private IDeporteService deporteService;

    @GetMapping
    public ResponseEntity<List<DeporteDTO>> getAllDeportes() {
        return ResponseEntity.ok(deporteService.getDeportes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeporteDTO> getDeporteById(@PathVariable Long id) {
        return ResponseEntity.ok(deporteService.getDeporteById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<DeporteDTO> createDeporte(@RequestBody DeporteCreateDTO deporte) {
        DeporteDTO creado = deporteService.createDeporte(deporte);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeporteDTO> updateDeporte(@PathVariable Long id, @RequestBody DeporteDTO deporte) {
        return ResponseEntity.ok(deporteService.updateDeporte(id, deporte));
    }

    @DeleteMapping("/{id}")
    public void deleteDeporte(@PathVariable Long id) {
        deporteService.deleteDeporte(id);
    }
}
