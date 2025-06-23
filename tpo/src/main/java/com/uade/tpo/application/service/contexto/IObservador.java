package com.uade.tpo.application.service.contexto;


import com.uade.tpo.application.dto.EstadoDTO;

public interface IObservador {
    void notificar(EstadoDTO estadoDTO);
}