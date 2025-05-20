package com.uade.tpo.application.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "estado_partido")

public class EstadoPartido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resultado_equipo_id")
    private Long id;  

    @OneToOne
    @JoinColumn(name = "partido_id", nullable = false) 
    private Partido partido;

    
    @Column(name = "nombre", nullable = false)
    private String nombre;
    
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "mensaje", nullable = false)
    private String mensaje;
}
