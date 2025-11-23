package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {

    // Use the CRUD methods provided by JpaRepository which use Integer ids.
}
