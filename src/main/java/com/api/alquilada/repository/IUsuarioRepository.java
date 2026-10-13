package com.api.alquilada.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.alquilada.model.UsuariosModel;

public interface IUsuarioRepository extends JpaRepository<UsuariosModel, Long> {

    Optional<UsuariosModel> findByEmail(String email);

    boolean existsByEmail(String email);
}
