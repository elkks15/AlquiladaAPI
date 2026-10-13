package com.api.alquilada.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.api.alquilada.model.MantenimientoModel;
import com.api.alquilada.service.MantenimientoService;

@RestController
@RequestMapping("/api/mantenimientos")
public class MantenimientoController {

    @Autowired
    private MantenimientoService mantenimientoService;

    @GetMapping
    public List<MantenimientoModel> listar() {
        return mantenimientoService.listar();
    }

    @GetMapping("/{id}")
    public MantenimientoModel obtener(@PathVariable Long id) {
        return mantenimientoService.obtener(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MantenimientoModel crear(@RequestBody MantenimientoModel mantenimiento) {
        return mantenimientoService.guardar(mantenimiento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        mantenimientoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
