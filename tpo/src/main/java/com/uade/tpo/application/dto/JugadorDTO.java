package com.uade.tpo.application.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class JugadorDTO {
    private Long id;
    private String nombre;
    private String email;
    private String ubicacion;
    private List<NivelDTO> niveles;
}
