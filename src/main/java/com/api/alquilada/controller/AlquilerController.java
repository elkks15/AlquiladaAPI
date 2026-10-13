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

import com.api.alquilada.model.AlquilerModel;
import com.api.alquilada.service.AlquilerService;

@RestController
@RequestMapping("/api/alquileres")
public class AlquilerController {

    @Autowired
    private AlquilerService alquilerService;

    @GetMapping
    public List<AlquilerModel> listar() {
        return alquilerService.listar();
    }

    @GetMapping("/{id}")
    public AlquilerModel obtener(@PathVariable Long id) {
        return alquilerService.obtener(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AlquilerModel crear(@RequestBody AlquilerModel alquiler) {
        return alquilerService.crear(alquiler);
    }

    @PutMapping("/{id}/finalizar")
    public AlquilerModel finalizar(@PathVariable Long id) {
        return alquilerService.finalizar(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        alquilerService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
