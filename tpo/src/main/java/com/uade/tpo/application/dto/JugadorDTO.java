package com.uade.tpo.application.dto;

import com.uade.tpo.application.enums.FormaNotificar;
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
    private FormaNotificar formaNotificar;
}
