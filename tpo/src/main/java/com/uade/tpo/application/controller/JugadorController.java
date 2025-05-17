package com.uade.tpo.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.uade.tpo.application.entity.Jugador;
import com.uade.tpo.application.service.jugador.IJugadorService;

@RestController
@RequestMapping("/api/jugador")
public class JugadorController {

    @Autowired
    private IJugadorService jugadorService;

    @GetMapping
    public List<Jugador> getAll() {
        return jugadorService.findAll();
    }

    @GetMapping("/{id}")
    public Jugador getById(@PathVariable Long id) {
        return jugadorService.findById(id)
                .orElseThrow(() -> new RuntimeException("Entity not found with id " + id));
    }

    @PostMapping
    public Jugador create(@RequestBody Jugador entity) {
        return jugadorService.save(entity);
    }

    @PutMapping("/{id}")
    public Jugador update(@PathVariable Long id, @RequestBody Jugador entity) {
        //entity.setId(id);
        return jugadorService.save(entity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        jugadorService.deleteById(id);
    }
}