package com.uade.tpo.application.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "resultado_final")
public class ResultadoFinal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resultado_final_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "partido_id", nullable = false) 
    private Partido partido;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "resultado_final_id")  
    private List<ResultadoEquipo> resultadosEquipos;  

    @Column(name = "ganador_id", nullable = false)
    private Long ganadorId;
}