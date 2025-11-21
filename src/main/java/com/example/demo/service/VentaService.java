package com.example.demo.service;

import com.example.demo.model.Usuario;
import com.example.demo.model.Venta;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Venta> obtenerTodas() {
        return ventaRepository.findAll();
    }

    public Optional<Venta> obtenerPorId(Long id) {
        return ventaRepository.findById(id);
    }

    public Venta crearVenta(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Venta venta = new Venta();
        venta.setUsuario(usuario);
        venta.setFechaVenta(LocalDateTime.now());

        return ventaRepository.save(venta);
    }

    public Venta guardarVenta(Venta venta) {
        return ventaRepository.save(venta);
    }

    public void eliminarVenta(Long id) {
        ventaRepository.deleteById(id);
    }

    public Venta actualizarVenta(Long id, Venta ventaActualizada) {
        ventaActualizada.setId(id);
        if (ventaActualizada.getUsuario() != null && ventaActualizada.getUsuario().getId() != null) {
            Usuario usuario = usuarioRepository.findById(ventaActualizada.getUsuario().getId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            ventaActualizada.setUsuario(usuario);
        }
        return ventaRepository.save(ventaActualizada);
    }

    public Optional<Venta> actualizarParcialmenteVenta(Long id, Venta datosActualizados) {
        Optional<Venta> ventaExistente = ventaRepository.findById(id);
        if (ventaExistente.isPresent()) {
            Venta venta = ventaExistente.get();
            if (datosActualizados.getUsuario() != null && datosActualizados.getUsuario().getId() != null) {
                Usuario usuario = usuarioRepository.findById(datosActualizados.getUsuario().getId())
                        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
                venta.setUsuario(usuario);
            }
            return Optional.of(ventaRepository.save(venta));
        }
        return Optional.empty();
    }
}