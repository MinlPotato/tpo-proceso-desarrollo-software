package com.uade.tpo.application.nivel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uade.tpo.application.nivel.entity.Nivel;

@Repository
public interface NivelRepository extends JpaRepository<Nivel, Long> {
}