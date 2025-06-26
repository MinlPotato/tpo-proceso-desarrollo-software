package com.uade.tpo.application.repository;

import com.uade.tpo.application.entity.Partido;
import com.uade.tpo.application.enums.EnumEstadoPartido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PartidoRepository extends JpaRepository<Partido, Long> {

    List<Partido> findByUbicacion(String ubicacionJugador);

    List<Partido> findByCreadorId(Long jugadorId);

    List<Partido> findByDeporteId(Long deporteId);

    List<Partido> findByEstadoAndHorarioLessThanEqual(EnumEstadoPartido estado, LocalDateTime horario);
    
    //List<Partido> findByEquipoId(Long equipoId);

}