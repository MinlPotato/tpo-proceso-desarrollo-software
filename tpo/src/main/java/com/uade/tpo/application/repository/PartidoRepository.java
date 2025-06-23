package com.uade.tpo.application.repository;

import com.uade.tpo.application.entity.Partido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartidoRepository extends JpaRepository<Partido, Long> {

    List<Partido> findByUbicacion(String ubicacionJugador);

    List<Partido> findByJugadorId(Long jugadorId);

    List<Partido> findByDeporteId(Long deporteId);

    List<Partido> findByNivel(Long nivelId);

    List<Partido> findByEquipoId(Long equipoId);

    List<Partido> findByDeporteIdyNivelID(Long deporteId, Long nivelId);
}