package com.uade.tpo.application.equipo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uade.tpo.application.equipo.entity.Equipo;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Long> {
}