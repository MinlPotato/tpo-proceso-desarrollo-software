package com.uade.tpo.application.controller;

import com.uade.tpo.application.dto.JugadorCreateDTO;
import com.uade.tpo.application.dto.JugadorDTO;
import com.uade.tpo.application.dto.NivelCreateDTO;
import com.uade.tpo.application.dto.NivelDTO;
import com.uade.tpo.application.service.nivel.INivelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.uade.tpo.application.service.jugador.IJugadorService;

@RestController
@RequestMapping("/api/jugadores")
public class JugadorController {

    @Autowired
    private IJugadorService jugadorService;

    @Autowired
    private INivelService nivelService;

    @GetMapping
    public ResponseEntity<List<JugadorDTO>> getJugadores() {
        return ResponseEntity.ok(jugadorService.getJugadores());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JugadorDTO> getJugador(@PathVariable Long id) {
        return ResponseEntity.ok(jugadorService.getJugadorById(id));
    }

    @PostMapping
    public ResponseEntity<JugadorDTO> createJugador(@RequestBody JugadorCreateDTO requestBody) {
        return ResponseEntity.status(HttpStatus.CREATED).body(jugadorService.createJugador(requestBody));
    }

    @PutMapping("/{jugadorId}/deportes")
    public ResponseEntity<NivelDTO> agregarDeporte(@PathVariable Long jugadorId, @RequestBody NivelCreateDTO requestBody) {
        return ResponseEntity.ok(nivelService.createNivel(jugadorId, requestBody));
    }

    @DeleteMapping("/{jugadorId}/deportes/{nivelId}")
    public void eliminarDeporte(@PathVariable Long jugadorId, @PathVariable Long nivelId) {
        nivelService.deleteNivel(jugadorId, nivelId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JugadorDTO> updateJugador(@PathVariable Long id, @RequestBody JugadorCreateDTO requestBody) {
        return ResponseEntity.ok(jugadorService.updateJugador(id, requestBody));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        jugadorService.eliminarJugador(id);
    }
}