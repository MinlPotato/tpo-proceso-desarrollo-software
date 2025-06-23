package com.uade.tpo.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uade.tpo.application.entity.Equipo;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Long> {
    static List<Equipo> findByJugador(Long jugadorId){
         throw new UnsupportedOperationException("Unimplemented method 'findByUbicacion'");
    }
}