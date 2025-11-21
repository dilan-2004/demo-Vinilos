package com.example.demo.service;

import com.example.demo.model.Region;
import com.example.demo.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;

    public List<Region> obtenerTodas() {
        return regionRepository.findAll();
    }

    public Optional<Region> obtenerPorId(Long id) {
        return regionRepository.findById(id);
    }

    public Region guardarRegion(Region region) {
        return regionRepository.save(region);
    }

    public void eliminarRegion(Long id) {
        regionRepository.deleteById(id);
    }

    public Region actualizarRegion(Long id, Region regionActualizada) {
        regionActualizada.setId(id);
        return regionRepository.save(regionActualizada);
    }

    public Optional<Region> actualizarParcialmenteRegion(Long id, Region datosActualizados) {
        Optional<Region> regionExistente = regionRepository.findById(id);
        if (regionExistente.isPresent()) {
            Region region = regionExistente.get();
            if (datosActualizados.getNombre() != null) {
                region.setNombre(datosActualizados.getNombre());
            }
            return Optional.of(regionRepository.save(region));
        }
        return Optional.empty();
    }
}