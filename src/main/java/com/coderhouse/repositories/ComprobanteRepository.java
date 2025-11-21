package com.coderhouse.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coderhouse.models.Cliente;
import com.coderhouse.models.Comprobante;
@Repository
public interface ComprobanteRepository extends JpaRepository<Comprobante, Long>{

	
    List<Comprobante> findByCliente(Cliente cliente); 
    

    List<Comprobante> findByFechaBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
}
