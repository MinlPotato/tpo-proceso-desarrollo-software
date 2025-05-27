package com.uade.tpo.application.resultadoEquipo.entity;

import com.uade.tpo.application.equipo.entity.Equipo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "resultado_equipo")
public class ResultadoEquipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resultado_equipo_id")
    private Long id;  

    @ManyToOne  // Relación muchos-a-uno 
    @JoinColumn(name = "id_equipo", nullable = false)
    private Equipo equipo;  

    @Column(name = "puntaje", nullable = false)
    private double puntaje;
}