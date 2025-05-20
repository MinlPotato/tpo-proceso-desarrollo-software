package com.uade.tpo.application.strategy;

import com.uade.tpo.application.entity.Partido;

public interface StrategyAdmisionPartido {
    
    boolean admit(Partido partido);
}