package com.uade.tpo.application.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.uade.tpo.application.enums.NivelDeporte;

@Getter
public class PartidoCreateDTO {
    private Long idCreador;
    private Long idDeporte;
    private Double duracion;
    private LocalDateTime horario;
    private String ubicacion;
    private Integer cantidadEquipos;
    private Integer cantidadJugadoresPorEquipo;
    private List<NivelDeporte> nivelesJugadores;
}
