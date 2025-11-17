package com.example.demo.controller;

import com.example.demo.model.Rol;
import com.example.demo.repository.RolRepository;
import com.example.demo.service.RolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@Tag(name = "Rol")
public class RolController {

    private static final Logger logger = LoggerFactory.getLogger(RolController.class);

    @Autowired
    private RolService rolService;

    @Autowired
    private RolRepository rolRepository; // Si necesitas métodos específicos del repositorio

    @GetMapping
    @Operation(summary = "Get all roles")
    public ResponseEntity<List<Rol>> getAllRoles() {
        try {
            List<Rol> roles = rolService.findAll();
            logger.info("Se encontraron {} roles.", roles.size());
            return ResponseEntity.ok(roles);
        } catch (Exception e) {
            logger.error("Error al obtener todos los roles", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a role by its ID")
    public ResponseEntity<Rol> getRolById(@PathVariable Integer id) {
        try {
            Rol rol = rolService.findById(id);
            if (rol == null) {
                logger.warn("Rol con ID {} no encontrado.", id);
                return ResponseEntity.notFound().build();
            }
            logger.info("Rol con ID {} encontrado.", id);
            return ResponseEntity.ok(rol);
        } catch (Exception e) {
            logger.error("Error al obtener rol por ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    @Operation(summary = "Create a new role")
    public ResponseEntity<Rol> createRol(@RequestBody Rol rol) {
        try {        
            if (rol.getNombre() == null || rol.getNombre().trim().isEmpty()) {
                 logger.error("Nombre del rol vacío al intentar crear rol.");
                 return ResponseEntity.badRequest().build();
            }
            if (rolRepository.findByNombre(rol.getNombre()).isPresent()) {
                 logger.error("Ya existe un rol con el nombre: {}", rol.getNombre());
                 return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409 Conflict
            }

            Rol savedRol = rolService.save(rol);
            logger.info("Rol '{}' creado exitosamente con ID: {}", rol.getNombre(), savedRol.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(savedRol);
        } catch (Exception e) {
            logger.error("Error al crear rol", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing role")
    public ResponseEntity<Rol> updateRol(@PathVariable Integer id, @RequestBody Rol rol) {
        try {
            Rol existingRol = rolService.findById(id);
            if (existingRol == null) {
                logger.warn("Rol con ID {} no encontrado para actualizar.", id);
                return ResponseEntity.notFound().build();
            }

            if (rol.getNombre() == null || rol.getNombre().trim().isEmpty()) {
                 logger.error("Nombre del rol vacío en actualización para ID: {}", id);
                 return ResponseEntity.badRequest().build();
            }

            existingRol.setNombre(rol.getNombre());

            Rol updatedRol = rolService.save(existingRol);
            logger.info("Rol con ID {} actualizado exitosamente.", id);
            return ResponseEntity.ok(updatedRol);
        } catch (Exception e) {
            logger.error("Error al actualizar rol con ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a role by its ID")
    public ResponseEntity<Void> deleteRol(@PathVariable Integer id) {
        try {
            rolService.deleteById(id);
            logger.info("Rol con ID {} eliminado exitosamente.", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error al eliminar rol con ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}