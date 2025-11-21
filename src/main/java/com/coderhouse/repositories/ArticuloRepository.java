package com.coderhouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coderhouse.models.Articulo;
@Repository
public interface ArticuloRepository extends JpaRepository<Articulo, Long> {

	boolean existsByNombre(String nombre);
}