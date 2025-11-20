package com.example.demo.controller;

import com.example.demo.model.Estado;
import com.example.demo.service.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estados")
public class EstadoController {

    @Autowired
    private EstadoService estadoService;

    @GetMapping
    public ResponseEntity<List<Estado>> listarEstados() {
        return ResponseEntity.ok(estadoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estado> obtenerEstado(@PathVariable Long id) {
        return estadoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Estado> crearEstado(@RequestBody Estado estado) {
        return ResponseEntity.ok(estadoService.guardarEstado(estado));
    }
}
