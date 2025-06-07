package com.uade.tpo.application.repository;

import com.uade.tpo.application.entity.Deporte;
import com.uade.tpo.application.entity.Jugador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uade.tpo.application.entity.Nivel;

@Repository
public interface NivelRepository extends JpaRepository<Nivel, Long> {

    boolean existsByJugadorAndDeporte(Jugador jugador, Deporte deporte);
    Nivel findByJugadorAndDeporte(Jugador jugador, Deporte deporte);

}