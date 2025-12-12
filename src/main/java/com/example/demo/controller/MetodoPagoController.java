package com.example.demo.controller;

import com.example.demo.model.MetodoPago;
import com.example.demo.service.MetodoPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/metodos-pago")
public class MetodoPagoController {

    @Autowired
    private MetodoPagoService metodoPagoService;

    @GetMapping
    public ResponseEntity<List<MetodoPago>> listarMetodosPago() {
        return ResponseEntity.ok(metodoPagoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MetodoPago> obtenerMetodoPago(@PathVariable Long id) {
        return metodoPagoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MetodoPago> crearMetodoPago(@RequestBody MetodoPago metodoPago) {
        return ResponseEntity.ok(metodoPagoService.guardarMetodoPago(metodoPago));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMetodoPago(@PathVariable Long id) {
        metodoPagoService.eliminarMetodoPago(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    @PutMapping("/{id}")
    public ResponseEntity<MetodoPago> actualizarMetodoPago(@PathVariable Long id,
            @RequestBody MetodoPago metodoPagoActualizado) {
        return metodoPagoService.obtenerPorId(id)
                .map(metodoPago -> ResponseEntity.ok(metodoPagoService.actualizarMetodoPago(id, metodoPagoActualizado)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MetodoPago> actualizarParcialmenteMetodoPago(@PathVariable Long id,
            @RequestBody MetodoPago datosActualizados) {
        return metodoPagoService.actualizarParcialmenteMetodoPago(id, datosActualizados)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
