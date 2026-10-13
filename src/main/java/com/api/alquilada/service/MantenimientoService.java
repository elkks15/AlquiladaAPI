package com.api.alquilada.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.alquilada.exceptions.RecursoNoEncontradoException;
import com.api.alquilada.model.MantenimientoModel;
import com.api.alquilada.model.VehiculoModel;
import com.api.alquilada.model.VehiculoModel.EstadoVehiculo;
import com.api.alquilada.repository.IMantenimientoRepository;

@Service
public class MantenimientoService {

    @Autowired
    private IMantenimientoRepository mantenimientoRepository;

    @Autowired
    private VehiculoService vehiculoService;

    public List<MantenimientoModel> listar() {
        return mantenimientoRepository.findAll();
    }

    public MantenimientoModel obtener(Long id) {
        return mantenimientoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe un mantenimiento con id: " + id));
    }

    @Transactional
    public MantenimientoModel guardar(MantenimientoModel request) {
        if (request.getVehiculo() == null || request.getVehiculo().getId() == null) {
            throw new IllegalArgumentException("Indica el vehiculo con su id.");
        }
        if (request.getDescripcion() == null || request.getDescripcion().isBlank()) {
            throw new IllegalArgumentException("La descripcion es obligatoria.");
        }
        if (request.getCosto() != null && request.getCosto() < 0) {
            throw new IllegalArgumentException("El costo no puede ser negativo.");
        }

        VehiculoModel vehiculo = vehiculoService.obtener(request.getVehiculo().getId());
        if (vehiculo.getEstado() == EstadoVehiculo.ALQUILADO) {
            throw new IllegalArgumentException("No se puede mandar a mantenimiento un vehiculo alquilado.");
        }
        vehiculo.setEstado(EstadoVehiculo.MANTENIMIENTO);
        vehiculoService.guardarEstado(vehiculo);

        MantenimientoModel mantenimiento = new MantenimientoModel();
        mantenimiento.setVehiculo(vehiculo);
        mantenimiento.setDescripcion(request.getDescripcion());
        mantenimiento.setCosto(request.getCosto());
        mantenimiento.setFechaMantenimiento(
                request.getFechaMantenimiento() != null ? request.getFechaMantenimiento() : LocalDate.now());
        return mantenimientoRepository.save(mantenimiento);
    }

    public void eliminar(Long id) {
        if (!mantenimientoRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("No existe un mantenimiento con id: " + id);
        }
        mantenimientoRepository.deleteById(id);
    }
}
