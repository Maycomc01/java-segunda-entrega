package com.coderhouse.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coderhouse.models.Articulo;
import com.coderhouse.models.Cliente;
import com.coderhouse.repositories.ArticuloRepository;
import com.coderhouse.repositories.ClienteRepository;



@Service
public class ClienteService {

    private final String messageCliente = "Cliente no encontrado";
    private final String messageArticulo = "Artículo no encontrado";
    
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ArticuloRepository articuloRepository;
    
    
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }
    
    
    
    public Cliente findById(Long id) {
        return clienteRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException(messageCliente));
    }
    
    
    @Transactional
    public Cliente save(Cliente cliente) {
        if(cliente.getDni() != 0 && clienteRepository.existsByDni(cliente.getDni())) {
            throw new IllegalStateException("El Cliente con este DNI ya existe");
        }
        
        return clienteRepository.save(cliente);
    }
    
    
    
    
    @Transactional
    public Cliente update(Long id, Cliente clienteActualizado) {
        Cliente cliente = findById(id);
        
        if (clienteActualizado.getNombre() != null && !clienteActualizado.getNombre().isEmpty()) {
			cliente.setNombre(clienteActualizado.getNombre());
		}
		
		if (clienteActualizado.getApellido() != null && !clienteActualizado.getApellido().isEmpty()) {
			cliente.setApellido(clienteActualizado.getApellido());
		}
        
        if (clienteActualizado.getDni() != 0) {
			cliente.setDni(clienteActualizado.getDni());
		}

        if (clienteActualizado.getEmail() != null && !clienteActualizado.getEmail().isEmpty()) {
			cliente.setEmail(clienteActualizado.getEmail());
		}
        
        return clienteRepository.save(cliente);
    }
    
    
    
    @Transactional
    public void deleteById(Long id) {
    	
    	
        if(!clienteRepository.existsById(id)) {
            throw new IllegalArgumentException(messageCliente);
        }
        clienteRepository.deleteById(id);
    }
    
    
    
    @Transactional
    public Cliente asociarArticulos(Long clienteId, List<Long> articuloIds) {
        Cliente cliente = findById(clienteId);
        
        
        List<Articulo> articulos = articuloRepository.findAllById(articuloIds);
        
        if (articulos.size() != articuloIds.size()) {
             throw new IllegalArgumentException(messageArticulo);
        }
        
        cliente.getArticulos().addAll(articulos);
        return clienteRepository.save(cliente);
    }
}
