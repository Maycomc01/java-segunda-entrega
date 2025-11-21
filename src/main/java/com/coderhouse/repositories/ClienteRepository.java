package com.coderhouse.repositories;

	import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coderhouse.models.Cliente;
	@Repository
	public interface ClienteRepository extends JpaRepository<Cliente, Long> {

		boolean existsByEmail(String email);
		boolean existsByDni(long dni);
		
	
	}

