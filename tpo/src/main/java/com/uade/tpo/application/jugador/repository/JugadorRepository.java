package com.uade.tpo.application.jugador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uade.tpo.application.jugador.entity.Jugador;


@Repository
public interface JugadorRepository extends JpaRepository<Jugador, Long> {
}