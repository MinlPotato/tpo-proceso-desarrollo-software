package com.uade.tpo.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uade.tpo.application.entity.Deporte;

@Repository
public interface DeporteRepository extends JpaRepository<Deporte, Long> {
    boolean existsByNombre(String nombre);
}