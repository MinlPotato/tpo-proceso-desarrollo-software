package com.uade.tpo.application.dto;

import com.uade.tpo.application.enums.NivelDeporte;
import lombok.Getter;

@Getter
public class NivelCreateDTO {
    private Long idDeporte;
    private NivelDeporte nivel;
    private Boolean favorito;
}
