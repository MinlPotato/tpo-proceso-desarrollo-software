package com.uade.tpo.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uade.tpo.application.entity.Nivel;

@Repository
public interface NivelRepository extends JpaRepository<Nivel, Long> {

    boolean exists(Long idJugador, Long idDeporte);
}