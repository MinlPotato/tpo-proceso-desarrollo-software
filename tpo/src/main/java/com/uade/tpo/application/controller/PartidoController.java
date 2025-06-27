package com.uade.tpo.application.controller;

import com.uade.tpo.application.dto.*;
import com.uade.tpo.application.entity.Equipo;
import com.uade.tpo.application.entity.Jugador;
import com.uade.tpo.application.entity.Partido;
import com.uade.tpo.application.enums.TipoFiltro;
import com.uade.tpo.application.factory.FactoryEstadoPartido;
import com.uade.tpo.application.service.partido.IPartidoService;
import com.uade.tpo.application.service.state.partido.EstadoPartido;
import org.jetbrains.annotations.NotNull;
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
    @Autowired
    private FactoryEstadoPartido factoryEstadoPartido;


    @GetMapping
    public ResponseEntity<List<PartidoDTO>> getAll() {
        return ResponseEntity.ok(partidoService.getPartidos()
            .stream()
            .map(this::toDTO)
            .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartidoDTO> getById(@PathVariable Long id) {
        Partido partido = partidoService.getPartidoById(id);
        return ResponseEntity.ok(toDTO(partido));
    }

    @PostMapping
    public ResponseEntity<PartidoDTO> create(@RequestBody PartidoCreateDTO dto) {
        Partido created = partidoService.createPartido(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PartidoDTO> update(@PathVariable Long id, @RequestBody PartidoDTO dto) {
        Partido partido = partidoService.updatePartido(id, dto);
        return ResponseEntity.ok(toDTO(partido));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        partidoService.deletePartido(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/agregar-jugador")
    public ResponseEntity<PartidoDTO> agregarJugador(@PathVariable Long id, @RequestBody AgregarJugadorDTO dto) {
        Partido partido = partidoService.agregarJugador(id, dto);
        return ResponseEntity.ok(toDTO(partido));
    }

    @PostMapping("/{id}/confirmar")
    public ResponseEntity<PartidoDTO> confirmarPartido(@PathVariable Long id) {
        Partido partido = partidoService.confirmarPartido(id);
        return ResponseEntity.ok(toDTO(partido));
    }

    @PostMapping("/{id}/finalizar")
    public ResponseEntity<PartidoDTO> finalizarPartido(@PathVariable Long id) {
        Partido partido = partidoService.finalizarPartido(id);
        return ResponseEntity.ok(toDTO(partido));
    }

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        partidoService.cancelarPartido(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/filtrar")
    public ResponseEntity<List<PartidoDTO>> filtrar(@RequestParam Long jugadorId, @RequestParam TipoFiltro tipofiltro) {
        List<Partido> partidosFiltrados = partidoService.filtrar(jugadorId, tipofiltro);

        return ResponseEntity.ok(partidosFiltrados
            .stream()
            .map(this::toDTO)
            .toList()
        );
    }

    @NotNull
    private PartidoDTO toDTO(Partido partido) {

        EstadoPartido estado = factoryEstadoPartido.crearEstadoPartido(partido.getEstado());

        EstadoDTO estadoDTO = new EstadoDTO(
            estado.getNombre(),
            estado.getDescripcion(),
            estado.getMensaje()
        );

        List<Equipo> equipos = partido.getEquipos();

        return new PartidoDTO(
            partido.getId(),
            (partido.getCreador() != null) ? partido.getCreador().getId() : null,
            (partido.getDeporte() != null) ? partido.getDeporte().getId() : null,
            partido.getDuracion(),
            partido.getHorario(),
            partido.getUbicacion(),
            partido.getCantidadEquipos(),
            partido.getCantidadJugadoresPorEquipo(),
            estadoDTO,
            equipos != null ? equipos.stream()
                .map(equipo -> new EquipoDTO(
                    equipo.getId(),
                    equipo.getNombre(),
                    equipo.getPartido() != null ? equipo.getPartido().getId() : null,
                    equipo.getJugadores() != null ? equipo.getJugadores().stream()
                        .map(Jugador::getId)
                        .toList() : List.of()
                )).toList() : List.of(),
            partido.getNivelesJugadores()
        );
    }

}