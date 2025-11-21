package com.example.demo.controller;

import com.example.demo.model.Direccion;
import com.example.demo.service.DireccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/direcciones")
public class DireccionController {

    @Autowired
    private DireccionService direccionService;

    @GetMapping
    public ResponseEntity<List<Direccion>> listarDirecciones() {
        return ResponseEntity.ok(direccionService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Direccion> obtenerDireccion(@PathVariable Long id) {
        return direccionService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Direccion> crearDireccion(@RequestBody Direccion direccion) {
        return ResponseEntity.ok(direccionService.guardarDireccion(direccion));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDireccion(@PathVariable Long id) {
        direccionService.eliminarDireccion(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Direccion> actualizarDireccion(@PathVariable Long id, @RequestBody Direccion direccionActualizada) {
        return direccionService.obtenerPorId(id)
                .map(direccion -> ResponseEntity.ok(direccionService.actualizarDireccion(id, direccionActualizada)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Direccion> actualizarParcialmenteDireccion(@PathVariable Long id, @RequestBody Direccion datosActualizados) {
        return direccionService.actualizarParcialmenteDireccion(id, datosActualizados)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
