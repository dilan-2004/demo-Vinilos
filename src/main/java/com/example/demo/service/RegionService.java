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
}