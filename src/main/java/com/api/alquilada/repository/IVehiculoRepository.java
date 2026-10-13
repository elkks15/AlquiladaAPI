package com.api.alquilada.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.alquilada.model.VehiculoModel;
import com.api.alquilada.model.VehiculoModel.EstadoVehiculo;

public interface IVehiculoRepository extends JpaRepository<VehiculoModel, Long> {

    List<VehiculoModel> findByEstado(EstadoVehiculo estado);
}
