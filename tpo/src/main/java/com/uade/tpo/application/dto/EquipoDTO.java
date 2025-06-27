package com.uade.tpo.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class EquipoDTO {
    private Long id;
    private String nombre;
    private Long idPartido;
    private List<Long> jugadores;
}
