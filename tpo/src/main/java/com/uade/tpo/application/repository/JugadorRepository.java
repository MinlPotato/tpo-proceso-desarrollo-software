package com.uade.tpo.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uade.tpo.application.entity.Jugador;


@Repository
public interface JugadorRepository extends JpaRepository<Jugador, Long> {
}