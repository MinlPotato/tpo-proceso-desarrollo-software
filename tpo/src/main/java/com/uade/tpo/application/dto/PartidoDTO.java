package com.uade.tpo.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.uade.tpo.application.enums.NivelDeporte;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class PartidoDTO {
    private Long id;
    private Long idCreador;
    private Long idDeporte;
    private Double duracion;
    private LocalDateTime horario;
    private String ubicacion;
    private Integer cantidadEquipos;
    private Integer cantidadJugadoresPorEquipo;
    private EstadoDTO estado;
    private List<EquipoDTO> equipos;
    private List<NivelDeporte> nivelesJugadores;

}