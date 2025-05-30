package com.uade.tpo.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uade.tpo.application.entity.Partido;

@Repository
public interface PartidoRepository extends JpaRepository<Partido, Long> {
}