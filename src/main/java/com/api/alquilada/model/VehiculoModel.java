package com.api.alquilada.model;

import com.api.alquilada.model.OficinaModel;

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
@Table(name = "vehiculos")

public class VehiculoModel {
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
private Long id;

@Column(nullable=false)
private String modelo;
private String marca;
private Integer anio;
private Integer tarifaDiaria;

@Column(nullable=false, unique=true)
private String placa;

@Enumerated(EnumType.STRING)
@Column(nullable=false )
private EstadoVehiculo estado;
public enum EstadoVehiculo{
    DISPONIBLE,
    ALQUILADO,
    MANTENIMIENTO
}

@ManyToOne
@JoinColumn(name="oficina_id", nullable= false)
private OficinaModel oficina;

public  VehiculoModel(){

}

public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public String getModelo() {
    return modelo;
}

public void setModelo(String modelo) {
    this.modelo = modelo;
}

public String getMarca() {
    return marca;
}

public void setMarca(String marca) {
    this.marca = marca;
}

public Integer getAnio() {
    return anio;
}

public void setAnio(Integer anio) {
    this.anio = anio;
}

public Integer getTarifaDiaria() {
    return tarifaDiaria;
}

public void setTarifaDiaria(Integer tarifaDiaria) {
    this.tarifaDiaria = tarifaDiaria;
}

public String getPlaca() {
    return placa;
}

public void setPlaca(String placa) {
    this.placa = placa;
}

public EstadoVehiculo getEstado() {
    return estado;
}

public void setEstado(EstadoVehiculo estado) {
    this.estado = estado;
}

public OficinaModel getOficina() {
    return oficina;
}

public void setOficina(OficinaModel oficina) {
    this.oficina = oficina;
}



}
