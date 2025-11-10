package com.coderhouse.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderhouse.models.Articulo;
import com.coderhouse.models.Cliente;
import com.coderhouse.repositories.ArticuloRepository;
import com.coderhouse.repositories.ClienteRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ArticuloRepository articuloRepository;

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Cliente findById(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
    }

    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente update(Long id, Cliente clienteActualizado) {
        Cliente cliente = findById(id);
        cliente.setNombre(clienteActualizado.getNombre());
        cliente.setApellido(clienteActualizado.getApellido());
        cliente.setDni(clienteActualizado.getDni());
        cliente.setEmail(clienteActualizado.getEmail());
        cliente.setEdad(clienteActualizado.getEdad());
        return clienteRepository.save(cliente);
    }

    public void deleteById(Long id) {
        Cliente cliente = findById(id);
        clienteRepository.delete(cliente);
    }

    public Cliente asociarArticulos(Long clienteId, List<Long> articuloIds) {
        Cliente cliente = findById(clienteId);
        List<Articulo> articulos = articuloRepository.findAllById(articuloIds);
        cliente.getArticulos().addAll(articulos);
        return clienteRepository.save(cliente);
    }
}
