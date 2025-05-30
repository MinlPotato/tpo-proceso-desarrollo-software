package com.uade.tpo.application.service.strategy.partido;

import com.uade.tpo.application.entity.Partido;


public interface StrategyAdmisionPartido {
    
    boolean admit(Partido partido);
}