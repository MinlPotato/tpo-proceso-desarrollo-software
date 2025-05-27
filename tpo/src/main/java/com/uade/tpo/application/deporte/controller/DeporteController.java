package com.uade.tpo.application.deporte.controller;

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
    public List<Deporte> getAllDeportes() {
        try {
            return deporteService.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of(); // Devuelve una lista vacía en caso de error
        }
    }

    @GetMapping("/{id}")
    public Deporte getDeporteById(@PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("ID must be a positive number");
            }
            else{
                return deporteService.findById(id)
                        .orElseThrow(() -> new RuntimeException("Deporte not found with id " + id));

            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

}
