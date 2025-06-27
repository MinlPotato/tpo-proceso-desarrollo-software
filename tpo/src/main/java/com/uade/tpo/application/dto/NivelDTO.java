package com.uade.tpo.application.dto;

import com.uade.tpo.application.enums.NivelDeporte;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class NivelDTO {
    private Long id;
    private Long idJugador;
    private Long idDeporte;
    private NivelDeporte nivel;
    private boolean favorito;
}
