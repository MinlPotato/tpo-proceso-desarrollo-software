package com.uade.tpo.application.repository;

import com.uade.tpo.application.entity.Deporte;
import com.uade.tpo.application.entity.Jugador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uade.tpo.application.entity.Nivel;

import java.util.List;

@Repository
public interface NivelRepository extends JpaRepository<Nivel, Long> {

    boolean existsByJugadorAndDeporte(Jugador jugador, Deporte deporte);

    Nivel findByJugadorAndDeporte(Jugador jugador, Deporte deporte);

    @Query("SELECT n.jugador FROM Nivel n WHERE n.deporte = :deporte AND n.favorito = true")
    List<Jugador> findJugadoresByDeporteFavorito(@Param("deporte") Deporte deporte);
}