package com.api.alquilada.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.api.alquilada.model.VehiculoModel;
import com.api.alquilada.service.VehiculoService;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {

    @Autowired
    private VehiculoService vehiculoService;

    @GetMapping
    public List<VehiculoModel> listar() {
        return vehiculoService.listar();
    }

    @GetMapping("/estado/{estado}")
    public List<VehiculoModel> listarPorEstado(@PathVariable String estado) {
        return vehiculoService.listarPorEstado(estado);
    }

    @GetMapping("/{id}")
    public VehiculoModel obtener(@PathVariable Long id) {
        return vehiculoService.obtener(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VehiculoModel crear(@RequestBody VehiculoModel vehiculo) {
        return vehiculoService.guardar(vehiculo);
    }

    @PutMapping("/{id}")
    public VehiculoModel actualizar(@PathVariable Long id, @RequestBody VehiculoModel vehiculo) {
        return vehiculoService.actualizar(id, vehiculo);
    }

    @PutMapping("/{id}/disponible")
    public VehiculoModel marcarDisponible(@PathVariable Long id) {
        return vehiculoService.marcarDisponible(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        vehiculoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
