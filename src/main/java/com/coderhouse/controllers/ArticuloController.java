package com.coderhouse.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.coderhouse.models.Articulo;
import com.coderhouse.services.ArticuloService;
import com.coderhouse.responses.ErrorResponses;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/articulos")
@Tag(name = "Gestion de Artículos", description = "Endpoints para gestionar los productos en stock")
public class ArticuloController {

    @Autowired
    private ArticuloService articuloService;

    @Operation(summary = "Obtener la lista de Todos los Artículos disponibles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de Artículos obtenida correctamente", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Articulo.class)))}),
            @ApiResponse(responseCode = "500", description = "Error Interno de Servidor", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = ErrorResponses.class)))
        })
    @GetMapping
    public ResponseEntity<List<Articulo>> obtenerTodos() {
        try {
            List<Articulo> articulos = articuloService.findAll();
            return ResponseEntity.ok(articulos);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Obtener un Artículo por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Artículo encontrado correctamente", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Articulo.class))}),
            @ApiResponse(responseCode = "404", description = "Error al Obtener el Artículo (No encontrado)", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = ErrorResponses.class))),
            @ApiResponse(responseCode = "500", description = "Error Interno de Servidor", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = ErrorResponses.class)))
        })
    @GetMapping("/{id}")
    public ResponseEntity<Articulo> obtenerPorId(
            @Parameter(description = "Identificador del artículo", example = "1", required = true)
            @PathVariable Long id) {
        try {
            Articulo articulo = articuloService.findById(id);
            return ResponseEntity.ok(articulo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Crear un Artículo (Producto) nuevo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Artículo creado correctamente", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Articulo.class))}),
            @ApiResponse(responseCode = "409", description = "Error al intentar crear el Artículo - CONFLICT", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = ErrorResponses.class))),
            @ApiResponse(responseCode = "500", description = "Error Interno de Servidor", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = ErrorResponses.class)))
        })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos del artículo a crear",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            name = "Artículo de Ejemplo",
                            value = "{\"nombre\":\"Mochila Kid\",\"precio\":250.50,\"stock\":50}"
                    ),
                    schema = @Schema(implementation = Articulo.class)
            )
    )
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

    @Operation(summary = "Actualizar un Artículo por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Artículo actualizado correctamente", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Articulo.class))}),
            @ApiResponse(responseCode = "404", description = "Error al obtener el Artículo (No encontrado)", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = ErrorResponses.class))),
            @ApiResponse(responseCode = "500", description = "Error Interno de Servidor", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = ErrorResponses.class)))
        })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos del artículo a actualizar",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            name = "Actualización de Stock",
                            value = "{\"precio\":260.00,\"stock\":45}"
                    ),
                    schema = @Schema(implementation = Articulo.class)
            )
    )
    @PutMapping("/{id}")
    public ResponseEntity<Articulo> actualizar(
            @Parameter(description = "Identificador del artículo", example = "1", required = true)
            @PathVariable Long id, 
            @RequestBody Articulo actualizado) {
        try {
            Articulo articulo = articuloService.update(id, actualizado);
            return ResponseEntity.ok(articulo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Eliminar un Artículo por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Artículo eliminado correctamente", content = {
                    @Content()}),
            @ApiResponse(responseCode = "404", description = "Error al obtener el Artículo (No encontrado)", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = ErrorResponses.class))),
            @ApiResponse(responseCode = "500", description = "Error Interno de Servidor", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = ErrorResponses.class)))
        })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "Identificador del artículo", example = "1", required = true)
            @PathVariable Long id) {
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