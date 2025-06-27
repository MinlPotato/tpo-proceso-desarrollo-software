package com.uade.tpo.application.dto;

public class EstadoDTO {
    private String nombre;
    private String descripcion;
    private String mensaje;

    public EstadoDTO() {}
    public EstadoDTO(String nombre, String descripcion, String mensaje) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.mensaje = mensaje;
    }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
}
