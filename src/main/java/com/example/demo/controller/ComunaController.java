package com.example.demo.controller;

import com.example.demo.model.Comuna;
import com.example.demo.service.ComunaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comunas")
public class ComunaController {

    @Autowired
    private ComunaService comunaService;

    @GetMapping
    public ResponseEntity<List<Comuna>> listarComunas() {
        return ResponseEntity.ok(comunaService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comuna> obtenerComuna(@PathVariable Long id) {
        return comunaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Comuna> crearComuna(@RequestBody Comuna comuna) {
        return ResponseEntity.ok(comunaService.guardarComuna(comuna));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarComuna(@PathVariable Long id) {
        comunaService.eliminarComuna(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comuna> actualizarComuna(@PathVariable Long id, @RequestBody Comuna comunaActualizada) {
        return comunaService.obtenerPorId(id)
                .map(comuna -> ResponseEntity.ok(comunaService.actualizarComuna(id, comunaActualizada)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Comuna> actualizarParcialmenteComuna(@PathVariable Long id, @RequestBody Comuna datosActualizados) {
        return comunaService.actualizarParcialmenteComuna(id, datosActualizados)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}