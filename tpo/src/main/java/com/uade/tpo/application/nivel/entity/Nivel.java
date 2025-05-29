package com.uade.tpo.application.nivel.entity;
import com.uade.tpo.application.deporte.entity.Deporte; 
import com.uade.tpo.application.jugador.entity.Jugador;
import com.uade.tpo.application.deporte.enums.NivelDeporte;   
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "nivel")
@Getter
@Setter
public class Nivel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nivel_id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "deporte_id", nullable = false)
    private Deporte deporte;

    @ManyToOne
    @JoinColumn(name = "jugador_id", nullable = false)
    private Jugador jugador;

    @Column(name = "nivel", nullable = false)
    private NivelDeporte nivel;

    @Column(name = "favorito", nullable = false)
    private Boolean favorito;
}
