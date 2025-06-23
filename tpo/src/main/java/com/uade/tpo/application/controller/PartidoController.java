package com.uade.tpo.application.controller;

import com.uade.tpo.application.dto.PartidoCreateDTO;
import com.uade.tpo.application.dto.PartidoDTO;
import com.uade.tpo.application.service.partido.IPartidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/partidos")
public class PartidoController {

    @Autowired
    private IPartidoService partidoService;

    @GetMapping
    public ResponseEntity<List<PartidoDTO>> getAll() {
        return ResponseEntity.ok(partidoService.getPartidos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartidoDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(partidoService.getPartidoById(id));
    }

    @PostMapping
    public ResponseEntity<PartidoDTO> create(@RequestBody PartidoCreateDTO dto) {
        PartidoDTO created = partidoService.createPartido(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PartidoDTO> update(@PathVariable Long id, @RequestBody PartidoDTO dto) {
        return ResponseEntity.ok(partidoService.updatePartido(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        partidoService.deletePartido(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/avanzar")
    public ResponseEntity<Void> avanzar(@PathVariable Long id) {
        partidoService.avanzarPartido(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        partidoService.cancelarPartido(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/filtrar")
    public ResponseEntity<List<PartidoDTO>> filtrar(
            @RequestParam Long jugadorId,
            @RequestParam String tipofiltro) {
        List<PartidoDTO> partidosFiltrados = partidoService.filtrar(jugadorId, tipofiltro);
        return ResponseEntity.ok(partidosFiltrados);
    }
}