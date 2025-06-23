package com.uade.tpo.application.dto;

import lombok.Getter;
import java.util.Date;

@Getter
public class PartidoCreateDTO {
    private Long idCreador;
    private Long idDeporte;
    private Double duracion;
    private Date horario;
    private String ubicacion;
    private Integer cantidadEquipos;
    private Integer cantidadJugadoresPorEquipo;
    private String tipoAdmision;
    private int minJugadoresNecesarios;
}
