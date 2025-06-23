package com.uade.tpo.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class PartidoDTO {
    private Long id;
    private Long idCreador;
    private Long idDeporte;
    private Double duracion;
    private Date horario;
    private String ubicacion;
    private Integer cantidadEquipos;
    private Integer cantidadJugadoresPorEquipo;
    private EstadoDTO estado;

}