package com.uade.tpo.application.dto;

import com.uade.tpo.application.enums.FormaNotificar;
import lombok.Getter;

@Getter
public class JugadorCreateDTO {
    private String nombre;
    private String email;
    private String ubicacion;
    private FormaNotificar formaNotificar;
}
