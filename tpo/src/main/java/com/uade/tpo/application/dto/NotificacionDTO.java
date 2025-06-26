package com.uade.tpo.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class NotificacionDTO {
    private String titulo;
    private String mensaje;
    private String email;

}
