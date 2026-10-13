package com.api.alquilada.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.alquilada.exceptions.RecursoNoEncontradoException;
import com.api.alquilada.model.OficinaModel;
import com.api.alquilada.repository.IOficinaRepository;

@Service
public class OficinaService {

    @Autowired
    private IOficinaRepository oficinaRepository;

    public List<OficinaModel> listar() {
        return oficinaRepository.findAll();
    }

    public OficinaModel obtener(Long id) {
        return oficinaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe una oficina con id: " + id));
    }

    public OficinaModel guardar(OficinaModel oficina) {
        validar(oficina);
        return oficinaRepository.save(oficina);
    }

    public OficinaModel actualizar(Long id, OficinaModel details) {
        OficinaModel actual = obtener(id);
        validar(details);
        actual.setNombreOficina(details.getNombreOficina());
        actual.setDireccion(details.getDireccion());
        actual.setCiudad(details.getCiudad());
        actual.setRegion(details.getRegion());
        return oficinaRepository.save(actual);
    }

    public void eliminar(Long id) {
        if (!oficinaRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("No existe una oficina con id: " + id);
        }
        oficinaRepository.deleteById(id);
    }

    private void validar(OficinaModel oficina) {
        if (oficina.getNombreOficina() == null || oficina.getNombreOficina().isBlank()) {
            throw new IllegalArgumentException("El nombre de la oficina es obligatorio.");
        }
    }
}
