package com.coderhouse.controllers;

import com.coderhouse.dto.ComprobanteDTO;
import com.coderhouse.models.Comprobante;
import com.coderhouse.responses.ErrorResponses;
import com.coderhouse.services.ComprobanteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comprobantes") 
@Tag(name = "Gestion de Comprobantes", description = "Endpoints para generar y consultar Comprobantes de Venta")
public class ComprobanteController {

    @Autowired 
    private ComprobanteService comprobanteService;

    
    @Operation(summary = "Obtener la lista de Todos los Comprobantes generados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de Comprobantes obtenida correctamente", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Comprobante.class)))}),
            @ApiResponse(responseCode = "500", description = "Error Interno de Servidor", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = ErrorResponses.class)))
        })
    @GetMapping
    public ResponseEntity<List<Comprobante>> obtenerTodos() {
        try {
            List<Comprobante> comprobantes = comprobanteService.findAll();
            return ResponseEntity.ok(comprobantes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build(); 
        }
    }
    
    
    @Operation(summary = "Obtener un Comprobante por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comprobante encontrado correctamente", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Comprobante.class))}),
            @ApiResponse(responseCode = "404", description = "Error al Obtener el Comprobante (No encontrado)", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = ErrorResponses.class))),
            @ApiResponse(responseCode = "500", description = "Error Interno de Servidor", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = ErrorResponses.class)))
        })
    @GetMapping("/{id}")
    public ResponseEntity<Comprobante> obtenerPorId(
            @Parameter(description = "Identificador del comprobante", example = "1", required = true)
            @PathVariable Long id) {
        try {
            Comprobante comprobante = comprobanteService.findById(id);
            return ResponseEntity.ok(comprobante);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build(); 
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    
    
    @Operation(summary = "Crea un nuevo Comprobante de Venta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comprobante creado correctamente", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Comprobante.class))}),
            @ApiResponse(responseCode = "404", description = "Cliente o Artículo no encontrado", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = ErrorResponses.class))),
            @ApiResponse(responseCode = "409", description = "Stock insuficiente - CONFLICT", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = ErrorResponses.class))),
            @ApiResponse(responseCode = "500", description = "Error Interno de Servidor", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = ErrorResponses.class)))
        })
    @PostMapping
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos del cliente y los artículos comprados para generar el comprobante",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            name = "Comprobante de ejemplo",
                            value = "{\"cliente\":{\"clienteid\":1}, \"detalleVenta\":[{\"cantidad\":2,\"articulo\":{\"productoid\":5}}]}"
                    ),
                    schema = @Schema(implementation = ComprobanteDTO.class)
            )
    )
    public ResponseEntity<?> crearComprobante(@RequestBody ComprobanteDTO request) {
        try {
            Comprobante comprobanteCreado = comprobanteService.crearComprobante(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(comprobanteCreado); // 201 Created
        } catch (IllegalArgumentException e) {
            ErrorResponses error = new ErrorResponses("Recurso No Encontrado", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error); // 404 Not Found
        } catch (IllegalStateException e) {
            ErrorResponses error = new ErrorResponses("Conflicto de Stock", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error); // 409 Conflict
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build(); // Error 500
        }
    }
}