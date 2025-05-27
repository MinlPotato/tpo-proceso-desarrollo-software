package com.uade.tpo.application.deporte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uade.tpo.application.deporte.entity.Deporte;

@Repository
public interface DeporteRepository extends JpaRepository<Deporte, Long> {
}