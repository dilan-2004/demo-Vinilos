package com.example.demo.controller;

import com.example.demo.model.Region;
import com.example.demo.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/regiones")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @GetMapping
    public ResponseEntity<List<Region>> listarRegiones() {
        return ResponseEntity.ok(regionService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Region> obtenerRegion(@PathVariable Long id) {
        return regionService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Region> crearRegion(@RequestBody Region region) {
        return ResponseEntity.ok(regionService.guardarRegion(region));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRegion(@PathVariable Long id) {
        regionService.eliminarRegion(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Region> actualizarRegion(@PathVariable Long id, @RequestBody Region regionActualizada) {
        return regionService.obtenerPorId(id)
                .map(region -> ResponseEntity.ok(regionService.actualizarRegion(id, regionActualizada)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Region> actualizarParcialmenteRegion(@PathVariable Long id, @RequestBody Region datosActualizados) {
        return regionService.actualizarParcialmenteRegion(id, datosActualizados)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
