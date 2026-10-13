package com.api.alquilada.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.alquilada.exceptions.RecursoNoEncontradoException;
import com.api.alquilada.model.UsuariosModel;
import com.api.alquilada.repository.IUsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    public List<UsuariosModel> listar() {
        return usuarioRepository.findAll();
    }

    public UsuariosModel obtener(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe un usuario con id: " + id));
    }

    public UsuariosModel guardar(UsuariosModel usuario) {
        validar(usuario);
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("Ya existe un usuario con ese email.");
        }
        aplicarValoresPorDefecto(usuario);
        return usuarioRepository.save(usuario);
    }

    public UsuariosModel actualizar(Long id, UsuariosModel details) {
        UsuariosModel actual = obtener(id);
        validar(details);
        usuarioRepository.findByEmail(details.getEmail()).ifPresent(existente -> {
            if (!existente.getId().equals(id)) {
                throw new IllegalArgumentException("Ya existe un usuario con ese email.");
            }
        });
        actual.setNombreUsuario(details.getNombreUsuario());
        actual.setApellidos(details.getApellidos());
        actual.setEdad(details.getEdad());
        actual.setEmail(details.getEmail());
        actual.setRol(details.getRol() == null || details.getRol().isBlank() ? actual.getRol() : details.getRol());
        if (details.getActivo() != null) {
            actual.setActivo(details.getActivo());
        }
        if (details.getPassword() != null && !details.getPassword().isBlank()) {
            actual.setPassword(details.getPassword());
        }
        return usuarioRepository.save(actual);
    }

    public void eliminar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("No existe un usuario con id: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    private void validar(UsuariosModel usuario) {
        if (usuario.getNombreUsuario() == null || usuario.getNombreUsuario().isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }
        if (usuario.getEmail() == null || usuario.getEmail().isBlank() || !usuario.getEmail().contains("@")) {
            throw new IllegalArgumentException("El email es obligatorio y debe tener un formato valido.");
        }
        if (usuario.getEdad() != null && usuario.getEdad() < 18) {
            throw new IllegalArgumentException("El cliente debe ser mayor de edad.");
        }
    }

    private void aplicarValoresPorDefecto(UsuariosModel usuario) {
        if (usuario.getRol() == null || usuario.getRol().isBlank()) {
            usuario.setRol("CLIENTE");
        }
        if (usuario.getPassword() == null || usuario.getPassword().isBlank()) {
            usuario.setPassword("N/A");
        }
        if (usuario.getActivo() == null) {
            usuario.setActivo(true);
        }
    }
}
