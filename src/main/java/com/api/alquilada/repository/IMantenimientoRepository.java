package com.api.alquilada.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.alquilada.model.MantenimientoModel;

public interface IMantenimientoRepository extends JpaRepository<MantenimientoModel, Long> {
}
