package com.api.alquilada.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.alquilada.model.AlquilerModel;

public interface IAlquilerRepositorio extends JpaRepository<AlquilerModel, Long> {
}
