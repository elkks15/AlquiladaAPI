package com.api.alquilada.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name="Alquileres")


public class AlquilerModel {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    @ManyToOne
    @JoinColumn(name="vehiculo_id")
    private VehiculoModel vehiculo;

    @ManyToOne
    @JoinColumn(name="cliente_id")
    private UsuariosModel cliente;

    @Column(nullable=false)
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Float precioTotal;

    @Enumerated(EnumType.STRING)
    @Column(nullable =false)
    private EstadoAlquiler estado;

    public enum EstadoAlquiler{
        PENDIENTE,
        ACTIVO,
        COMPLETADO
    }

    public AlquilerModel(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public VehiculoModel getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(VehiculoModel vehiculo) {
        this.vehiculo = vehiculo;
    }

    @JsonProperty("cliente")
    public UsuariosModel getUsuario() {
        return cliente;
    }

    @JsonProperty("cliente")
    public void setUsuario(UsuariosModel usuario) {
        this.cliente = usuario;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Float getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(Float precioTotal) {
        this.precioTotal = precioTotal;
    }

    public EstadoAlquiler getEstado() {
        return estado;
    }

    public void setEstado(EstadoAlquiler estado) {
        this.estado = estado;
    }

    


}
