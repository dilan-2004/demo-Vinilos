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
}
