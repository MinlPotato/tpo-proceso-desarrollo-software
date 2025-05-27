package com.uade.tpo.application.partido.strategy;

import com.uade.tpo.application.partido.entity.Partido;

public class EmparejarPorNivel implements StrategyAdmisionPartido {
    private int minimo;
    private int maximo;

    public EmparejarPorNivel(int minimo, int maximo) {
        this.minimo = minimo;
        this.maximo = maximo;
    }

    @Override
    public boolean admit(Partido partido) {
        int nivelPartido = 5; // revisar el nivel del partido
        return nivelPartido >= minimo && nivelPartido <= maximo;
    }
}