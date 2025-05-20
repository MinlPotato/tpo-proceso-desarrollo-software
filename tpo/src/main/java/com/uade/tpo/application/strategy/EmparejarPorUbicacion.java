package com.uade.tpo.application.strategy;

import com.uade.tpo.application.entity.Partido;

public class EmparejarPorUbicacion implements StrategyAdmisionPartido {
    private String ubicacion;

    public EmparejarPorUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    @Override
    public boolean admit(Partido partido) {
        // Lógica para validar ubicación
        return partido.getUbicacion().equalsIgnoreCase(ubicacion);
    }
} 
