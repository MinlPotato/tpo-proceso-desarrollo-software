package com.uade.tpo.application.partido.strategy;

import com.uade.tpo.application.partido.entity.Partido;

public interface StrategyAdmisionPartido {
    
    boolean admit(Partido partido);
}