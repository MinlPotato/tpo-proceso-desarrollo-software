package com.uade.tpo.application.service.strategy.partido;

import com.uade.tpo.application.entity.Partido;

public class EmparejarPorHistorial implements StrategyAdmisionPartido {
    private String ubicacion;

    public EmparejarPorHistorial(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    @Override
    public boolean admit(Partido partido) {
        // Lógica más compleja que considera historial de partidos
        // Implementación de ejemplo:
        return partido.getUbicacion().equalsIgnoreCase(ubicacion);
               
    }

    
}