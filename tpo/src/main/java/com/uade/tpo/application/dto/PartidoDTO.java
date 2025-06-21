package com.uade.tpo.application.dto;

import java.util.Date;

public class PartidoDTO {
    private Long id;
    private Long idCreador;
    private Long idDeporte;
    private Double duracion;
    private Date horario;
    private String ubicacion;
    private Integer cantidadEquipos;
    private Integer cantidadJugadoresPorEquipo;
    private EstadoDTO estado;

    public PartidoDTO(Long long1, Double double1, Date date, String string, Long long2) {}

    public PartidoDTO(Long id, Long idCreador, Long idDeporte, Double duracion, Date horario,
                      String ubicacion, Integer cantidadEquipos, Integer cantidadJugadoresPorEquipo,
                       EstadoDTO estado) {
        this.id = id;
        this.idCreador = idCreador;
        this.idDeporte = idDeporte;
        this.duracion = duracion;
        this.horario = horario;
        this.ubicacion = ubicacion;
        this.cantidadEquipos = cantidadEquipos;
        this.cantidadJugadoresPorEquipo = cantidadJugadoresPorEquipo;
        this.estado = estado;
    }

    // Getters and setters...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getIdCreador() { return idCreador; }
    public void setIdCreador(Long idCreador) { this.idCreador = idCreador; }
    public Long getIdDeporte() { return idDeporte; }
    public void setIdDeporte(Long idDeporte) { this.idDeporte = idDeporte; }
    public Double getDuracion() { return duracion; }
    public void setDuracion(Double duracion) { this.duracion = duracion; }
    public Date getHorario() { return horario; }
    public void setHorario(Date horario) { this.horario = horario; }
    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    public Integer getCantidadEquipos() { return cantidadEquipos; }
    public void setCantidadEquipos(Integer cantidadEquipos) { this.cantidadEquipos = cantidadEquipos; }
    public Integer getCantidadJugadoresPorEquipo() { return cantidadJugadoresPorEquipo; }
    public void setCantidadJugadoresPorEquipo(Integer cantidadJugadoresPorEquipo) { this.cantidadJugadoresPorEquipo = cantidadJugadoresPorEquipo; }
    public EstadoDTO getEstado() { return estado; }
    public void setEstado(EstadoDTO estado) { this.estado = estado; }
}