package com.coderhouse.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.coderhouse.models.Articulo;
import com.coderhouse.services.ArticuloService;
import com.coderhouse.responses.ErrorResponses;

@RestController
@RequestMapping("/articulos")
public class ArticuloController {

    @Autowired
    private ArticuloService articuloService;


    @GetMapping
    public ResponseEntity<List<Articulo>> obtenerTodos() {
        try {
            List<Articulo> articulos = articuloService.findAll();
            return ResponseEntity.ok(articulos);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

  
    @GetMapping("/{id}")
    public ResponseEntity<Articulo> obtenerPorId(@PathVariable Long id) {
        try {
            Articulo articulo = articuloService.findById(id);
            return ResponseEntity.ok(articulo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Articulo articulo) {
        try {
            Articulo creado = articuloService.save(articulo);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (IllegalStateException e) {
            ErrorResponses error = new ErrorResponses("Conflicto", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Articulo> actualizar(@PathVariable Long id, @RequestBody Articulo actualizado) {
        try {
            Articulo articulo = articuloService.update(id, actualizado);
            return ResponseEntity.ok(articulo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            articuloService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
