package com.coderhouse.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderhouse.interfaces.CRUDInterface;
import com.coderhouse.models.Articulo;
import com.coderhouse.repositories.ArticuloRepository;

import jakarta.transaction.Transactional;

@Service
public class ArticuloService implements CRUDInterface<Articulo, Long> {

	private final String message = "Articulo no encontrado";
	
	@Autowired
	private ArticuloRepository repo;
	
	
	@Override
	public List<Articulo> findAll() {
		return repo.findAll();
	}

	@Override
	public Articulo findById(Long id) {
		return repo.findById(id)
				.orElseThrow(() -> new IllegalArgumentException(message));
	}

	@Override
	@Transactional
	public Articulo save(Articulo articuloNuevo) {
		if(articuloNuevo.getNombre() != null && repo.existsByNombre(articuloNuevo.getNombre())) {
			throw new IllegalStateException("El curso con este Nombre ya existe");
		}
		return repo.save(articuloNuevo);
	}

	@Override
	@Transactional
	public Articulo update(Long id, Articulo articuloActualizado) {
		Articulo articulo = findById(id);
		
		if(articuloActualizado.getNombre() != null && !articuloActualizado.getNombre().isEmpty()) {
			articulo.setNombre(articuloActualizado.getNombre());
		}
		
		return repo.save(articulo);		
	}

	@Override
	public void deleteById(Long id) {
		if(!repo.existsById(id)) {
			throw new IllegalArgumentException(message);
		}
		repo.deleteById(id);
	}
}