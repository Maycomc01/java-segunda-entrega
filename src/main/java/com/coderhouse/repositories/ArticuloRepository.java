package com.coderhouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coderhouse.models.Articulo;

public interface ArticuloRepository extends JpaRepository<Articulo, Long> {

	boolean existsByNombre(String nombre);
}