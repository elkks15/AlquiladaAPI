package com.api.alquilada.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.alquilada.model.OficinaModel;

public interface IOficinaRepository extends JpaRepository<OficinaModel, Long> {
}
