package com.uade.tpo.application.service.state.partido;

import com.uade.tpo.application.entity.Partido;

public interface EstadoPartido {
    EstadoPartido avanzar(Partido p);
    EstadoPartido cancelar(Partido p);
    String getNombre();
    String getDescripcion();
    String getMensaje();
}