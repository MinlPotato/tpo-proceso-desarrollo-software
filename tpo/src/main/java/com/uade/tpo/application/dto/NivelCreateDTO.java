package com.uade.tpo.application.dto;

public class NivelCreateDTO {
    private Long idJugador;
    private Long idDeporte;
    private String nivel;
    private boolean favorito;

    // Constructor completo
    public NivelCreateDTO(Long idJugador, Long idDeporte, String nivel, Boolean favorito) {
        this.idJugador = idJugador;
        this.idDeporte = idDeporte;
        this.nivel = nivel;
        this.favorito = favorito;
    }

    // Getters y Setters
    public Long getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(Long idJugador) {
        this.idJugador = idJugador;
    }

    public Long getIdDeporte() {
        return idDeporte;
    }

    public void setIdDeporte(Long idDeporte) {
        this.idDeporte = idDeporte;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public Boolean isFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }
}
