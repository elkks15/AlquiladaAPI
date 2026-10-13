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

import com.api.alquilada.model.OficinaModel;
import com.api.alquilada.service.OficinaService;

@RestController
@RequestMapping("/api/oficinas")
public class OficinaController {

    @Autowired
    private OficinaService oficinaService;

    @GetMapping
    public List<OficinaModel> listar() {
        return oficinaService.listar();
    }

    @GetMapping("/{id}")
    public OficinaModel obtener(@PathVariable Long id) {
        return oficinaService.obtener(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OficinaModel crear(@RequestBody OficinaModel oficina) {
        return oficinaService.guardar(oficina);
    }

    @PutMapping("/{id}")
    public OficinaModel actualizar(@PathVariable Long id, @RequestBody OficinaModel oficina) {
        return oficinaService.actualizar(id, oficina);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        oficinaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
