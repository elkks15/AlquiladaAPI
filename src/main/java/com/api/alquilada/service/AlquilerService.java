package com.api.alquilada.service;

import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.alquilada.exceptions.RecursoNoEncontradoException;
import com.api.alquilada.model.AlquilerModel;
import com.api.alquilada.model.AlquilerModel.EstadoAlquiler;
import com.api.alquilada.model.UsuariosModel;
import com.api.alquilada.model.VehiculoModel;
import com.api.alquilada.model.VehiculoModel.EstadoVehiculo;
import com.api.alquilada.repository.IAlquilerRepositorio;

@Service
public class AlquilerService {

    @Autowired
    private IAlquilerRepositorio alquilerRepositorio;

    @Autowired
    private VehiculoService vehiculoService;

    @Autowired
    private UsuarioService usuarioService;

    public List<AlquilerModel> listar() {
        return alquilerRepositorio.findAll();
    }

    public AlquilerModel obtener(Long id) {
        return alquilerRepositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe un alquiler con id: " + id));
    }

    @Transactional
    public AlquilerModel crear(AlquilerModel request) {
        if (request.getVehiculo() == null || request.getVehiculo().getId() == null) {
            throw new IllegalArgumentException("Indica el vehiculo con su id.");
        }
        if (request.getUsuario() == null || request.getUsuario().getId() == null) {
            throw new IllegalArgumentException("Indica el cliente con su id.");
        }
        if (request.getFechaInicio() == null || request.getFechaFin() == null) {
            throw new IllegalArgumentException("Las fechas de inicio y fin son obligatorias.");
        }

        long dias = ChronoUnit.DAYS.between(request.getFechaInicio(), request.getFechaFin());
        if (dias < 1) {
            throw new IllegalArgumentException("La fecha fin debe ser posterior a la fecha inicio.");
        }

        VehiculoModel vehiculo = vehiculoService.obtener(request.getVehiculo().getId());
        UsuariosModel cliente = usuarioService.obtener(request.getUsuario().getId());

        if (vehiculo.getEstado() != EstadoVehiculo.DISPONIBLE) {
            throw new IllegalArgumentException("El vehiculo no esta disponible.");
        }
        if (!Boolean.TRUE.equals(cliente.getActivo())) {
            throw new IllegalArgumentException("El cliente no esta activo.");
        }
        if (!"CLIENTE".equalsIgnoreCase(cliente.getRol())) {
            throw new IllegalArgumentException("Solo un usuario con rol CLIENTE puede alquilar.");
        }

        vehiculo.setEstado(EstadoVehiculo.ALQUILADO);
        vehiculoService.guardarEstado(vehiculo);

        AlquilerModel alquiler = new AlquilerModel();
        alquiler.setVehiculo(vehiculo);
        alquiler.setUsuario(cliente);
        alquiler.setFechaInicio(request.getFechaInicio());
        alquiler.setFechaFin(request.getFechaFin());
        alquiler.setPrecioTotal((float) dias * vehiculo.getTarifaDiaria());
        alquiler.setEstado(EstadoAlquiler.ACTIVO);
        return alquilerRepositorio.save(alquiler);
    }

    @Transactional
    public AlquilerModel finalizar(Long id) {
        AlquilerModel alquiler = obtener(id);
        if (alquiler.getEstado() == EstadoAlquiler.COMPLETADO) {
            throw new IllegalArgumentException("Ese alquiler ya fue finalizado.");
        }
        VehiculoModel vehiculo = alquiler.getVehiculo();
        vehiculo.setEstado(EstadoVehiculo.DISPONIBLE);
        vehiculoService.guardarEstado(vehiculo);
        alquiler.setEstado(EstadoAlquiler.COMPLETADO);
        return alquilerRepositorio.save(alquiler);
    }

    public void eliminar(Long id) {
        AlquilerModel alquiler = obtener(id);
        if (alquiler.getEstado() == EstadoAlquiler.ACTIVO) {
            throw new IllegalArgumentException("Finaliza el alquiler antes de eliminarlo.");
        }
        alquilerRepositorio.deleteById(id);
    }
}
