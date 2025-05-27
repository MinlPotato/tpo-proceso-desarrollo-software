package com.uade.tpo.application.partido.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uade.tpo.application.partido.entity.Partido;

@Repository
public interface PartidoRepository extends JpaRepository<Partido, Long> {
}