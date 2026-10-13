package com.api.alquilada.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.alquilada.exceptions.RecursoNoEncontradoException;
import com.api.alquilada.model.OficinaModel;
import com.api.alquilada.model.VehiculoModel;
import com.api.alquilada.model.VehiculoModel.EstadoVehiculo;
import com.api.alquilada.repository.IVehiculoRepository;

@Service
public class VehiculoService {

    @Autowired
    private IVehiculoRepository vehiculoRepository;

    @Autowired
    private OficinaService oficinaService;

    public List<VehiculoModel> listar() {
        return vehiculoRepository.findAll();
    }

    public List<VehiculoModel> listarPorEstado(String estado) {
        return vehiculoRepository.findByEstado(parseEstado(estado));
    }

    public VehiculoModel obtener(Long id) {
        return vehiculoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe un vehiculo con id: " + id));
    }

    public VehiculoModel guardar(VehiculoModel vehiculo) {
        validar(vehiculo);
        vehiculo.setOficina(resolverOficina(vehiculo));
        if (vehiculo.getEstado() == null) {
            vehiculo.setEstado(EstadoVehiculo.DISPONIBLE);
        }
        return vehiculoRepository.save(vehiculo);
    }

    public VehiculoModel actualizar(Long id, VehiculoModel details) {
        VehiculoModel actual = obtener(id);
        validar(details);
        actual.setOficina(resolverOficina(details));
        actual.setModelo(details.getModelo());
        actual.setMarca(details.getMarca());
        actual.setAnio(details.getAnio());
        actual.setTarifaDiaria(details.getTarifaDiaria());
        actual.setPlaca(details.getPlaca());
        if (details.getEstado() != null) {
            actual.setEstado(details.getEstado());
        }
        return vehiculoRepository.save(actual);
    }

    public VehiculoModel guardarEstado(VehiculoModel vehiculo) {
        return vehiculoRepository.save(vehiculo);
    }

    public VehiculoModel marcarDisponible(Long id) {
        VehiculoModel vehiculo = obtener(id);
        if (vehiculo.getEstado() == EstadoVehiculo.ALQUILADO) {
            throw new IllegalArgumentException("No se puede liberar un vehiculo que sigue alquilado. Finaliza el alquiler primero.");
        }
        vehiculo.setEstado(EstadoVehiculo.DISPONIBLE);
        return vehiculoRepository.save(vehiculo);
    }

    public void eliminar(Long id) {
        if (!vehiculoRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("No existe un vehiculo con id: " + id);
        }
        vehiculoRepository.deleteById(id);
    }

    private OficinaModel resolverOficina(VehiculoModel vehiculo) {
        if (vehiculo.getOficina() == null || vehiculo.getOficina().getId() == null) {
            throw new IllegalArgumentException("Indica la oficina con su id.");
        }
        return oficinaService.obtener(vehiculo.getOficina().getId());
    }

    private void validar(VehiculoModel vehiculo) {
        if (vehiculo.getModelo() == null || vehiculo.getModelo().isBlank()) {
            throw new IllegalArgumentException("El modelo es obligatorio.");
        }
        if (vehiculo.getPlaca() == null || vehiculo.getPlaca().isBlank()) {
            throw new IllegalArgumentException("La placa es obligatoria.");
        }
        if (vehiculo.getTarifaDiaria() == null || vehiculo.getTarifaDiaria() <= 0) {
            throw new IllegalArgumentException("La tarifa diaria debe ser mayor a 0.");
        }
    }

    private EstadoVehiculo parseEstado(String estado) {
        try {
            return EstadoVehiculo.valueOf(estado.trim().toUpperCase());
        } catch (Exception ex) {
            throw new IllegalArgumentException("Estado no valido. Usa DISPONIBLE, ALQUILADO o MANTENIMIENTO.");
        }
    }
}
