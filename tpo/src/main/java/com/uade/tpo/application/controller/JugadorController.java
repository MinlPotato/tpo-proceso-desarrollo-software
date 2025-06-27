package com.uade.tpo.application.controller;

import com.uade.tpo.application.dto.JugadorCreateDTO;
import com.uade.tpo.application.dto.JugadorDTO;
import com.uade.tpo.application.dto.NivelCreateDTO;
import com.uade.tpo.application.dto.NivelDTO;
import com.uade.tpo.application.entity.Jugador;
import com.uade.tpo.application.entity.Nivel;
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
        return ResponseEntity.ok(jugadorService.getJugadores()
            .stream()
            .map(this::toDTO)
            .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<JugadorDTO> getJugador(@PathVariable Long id) {
        Jugador jugador = jugadorService.getJugadorById(id);
        return ResponseEntity.ok(toDTO(jugador));
    }

    @PostMapping
    public ResponseEntity<JugadorDTO> createJugador(@RequestBody JugadorCreateDTO requestBody) {
        Jugador jugador = jugadorService.createJugador(requestBody);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(jugador));
    }

    @PutMapping("/{jugadorId}/deportes")
    public ResponseEntity<NivelDTO> agregarDeporte(@PathVariable Long jugadorId, @RequestBody NivelCreateDTO requestBody) {
        Nivel nivel = nivelService.createNivel(jugadorId, requestBody);
        return ResponseEntity.ok(toDTO(nivel));
    }

    @DeleteMapping("/{jugadorId}/deportes/{nivelId}")
    public void eliminarDeporte(@PathVariable Long jugadorId, @PathVariable Long nivelId) {
        nivelService.deleteNivel(jugadorId, nivelId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JugadorDTO> updateJugador(@PathVariable Long id, @RequestBody JugadorCreateDTO requestBody) {
        Jugador jugador = jugadorService.updateJugador(id, requestBody);
        return ResponseEntity.ok(toDTO(jugador));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        jugadorService.eliminarJugador(id);
    }

    private NivelDTO toDTO(Nivel nivel) {
        return new NivelDTO(
            nivel.getId(),
            nivel.getJugador().getId(),
            nivel.getDeporte().getId(),
            nivel.getNivel(), // o nivel.getNivel().toString()
            nivel.getFavorito()
        );
    }

    private JugadorDTO toDTO(Jugador jugador) {
        List<NivelDTO> nivelDTOS = jugador.getNiveles()
            .stream()
            .map(nivel -> new NivelDTO(nivel.getId(), nivel.getJugador().getId(), nivel.getDeporte().getId(), nivel.getNivel(), nivel.getFavorito()))
            .toList();

        return new JugadorDTO(
            jugador.getId(),
            jugador.getUser().getId(),
            jugador.getNombre(),
            jugador.getEmail(),
            jugador.getUbicacion(),
            nivelDTOS,
            jugador.getFormaNotificar()
        );
    }
}