package com.coderhouse.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.coderhouse.models.Cliente;
import com.coderhouse.services.ClienteService;
import com.coderhouse.responses.ErrorResponses;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<Cliente>> getAllCliente() {
        try {
            List<Cliente> cliente = clienteService.findAll();
            return ResponseEntity.ok(cliente);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Long id) {
        try {
            Cliente cliente = clienteService.findById(id);
            return ResponseEntity.ok(cliente);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createCliente(@RequestBody Cliente cliente) {
        try {
            Cliente clienteCreado = clienteService.save(cliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(clienteCreado);
        } catch (IllegalStateException e) {
            ErrorResponses error = new ErrorResponses("Conflicto", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateClienteById(@PathVariable Long id, @RequestBody Cliente clienteActualizado) {
        try {
            Cliente cliente = clienteService.update(id, clienteActualizado);
            return ResponseEntity.ok(cliente);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClienteById(@PathVariable Long id) {
        try {
            clienteService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

  
    @PostMapping("/{id}/articulos")
    public ResponseEntity<Cliente> asociarArticulos(@PathVariable Long id, @RequestBody List<Long> articuloIds) {
        try {
            Cliente clienteActualizado = clienteService.asociarArticulos(id, articuloIds);
            return ResponseEntity.ok(clienteActualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
