package com.coderhouse.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.coderhouse.models.Cliente;
import com.coderhouse.services.ClienteService;
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
@RequestMapping("/api/clientes")
@Tag(name = "Gestion de Clientes", description = "Endpoints para gestionar la información de los clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;


    @Operation(summary = "Obtener la lista de Todos los Clientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de Clientes obtenida correctamente", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Cliente.class)))}),
            @ApiResponse(responseCode = "500", description = "Error Interno de Servidor", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = ErrorResponses.class)))
        })
    @GetMapping
    public ResponseEntity<List<Cliente>> obtenerTodos() {
        try {
            List<Cliente> clientes = clienteService.findAll();
            return ResponseEntity.ok(clientes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    
    @Operation(summary = "Obtener un Cliente por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado correctamente", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))}),
            @ApiResponse(responseCode = "404", description = "Error al Obtener el Cliente (No encontrado)", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = ErrorResponses.class))),
            @ApiResponse(responseCode = "500", description = "Error Interno de Servidor", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = ErrorResponses.class)))
        })
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerPorId(
            @Parameter(description = "Identificador del cliente", example = "1", required = true)
            @PathVariable Long id) {
        try {
            Cliente cliente = clienteService.findById(id);
            return ResponseEntity.ok(cliente);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    
    @Operation(summary = "Crear un Cliente nuevo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente creado correctamente", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))}),
            @ApiResponse(responseCode = "409", description = "Error al intentar crear el Cliente - CONFLICT (DNI existente)", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = ErrorResponses.class))),
            @ApiResponse(responseCode = "500", description = "Error Interno de Servidor", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = ErrorResponses.class)))
        })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos del cliente a crear",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            name = "Cliente de Ejemplo",
                            value = "{\"nombre\":\"Juan\",\"apellido\":\"Perez\",\"dni\":12345678,\"email\":\"juan.perez@test.com\"}"
                    ),
                    schema = @Schema(implementation = Cliente.class)
            )
    )
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Cliente cliente) {
        try {
            Cliente creado = clienteService.save(cliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (IllegalStateException e) {
            ErrorResponses error = new ErrorResponses("Conflicto de Datos", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    
    @Operation(summary = "Actualizar los datos de un Cliente por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente actualizado correctamente", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))}),
            @ApiResponse(responseCode = "404", description = "Error al obtener el Cliente (No encontrado)", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = ErrorResponses.class))),
            @ApiResponse(responseCode = "500", description = "Error Interno de Servidor", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = ErrorResponses.class)))
        })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos del cliente a actualizar",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            name = "Actualización de Email",
                            value = "{\"email\":\"nuevo.email@test.com\"}"
                    ),
                    schema = @Schema(implementation = Cliente.class)
            )
    )
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizar(
            @Parameter(description = "Identificador del cliente", example = "1", required = true)
            @PathVariable Long id, 
            @RequestBody Cliente clienteActualizado) {
        try {
            Cliente cliente = clienteService.update(id, clienteActualizado);
            return ResponseEntity.ok(cliente);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    
    @Operation(summary = "Asociar una lista de Artículos a un Cliente (Favoritos)",
               description = "Agrega uno o más IDs de artículos a la lista de favoritos del cliente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Artículos asociados correctamente", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))}),
            @ApiResponse(responseCode = "404", description = "Cliente o Artículo no encontrado", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = ErrorResponses.class))),
            @ApiResponse(responseCode = "500", description = "Error Interno de Servidor", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = ErrorResponses.class)))
        })
    @PutMapping("/{id}/articulos")
    public ResponseEntity<?> asociarArticulos(
            @Parameter(description = "Identificador del cliente", example = "1", required = true)
            @PathVariable Long id, 
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Lista de IDs de artículos a asociar",
                required = true,
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "IDs de Artículos",
                        value = "[1, 5, 8]"
                    ),
                    array = @ArraySchema(schema = @Schema(implementation = Long.class))
                )
            )
            @RequestBody List<Long> articuloIds) {
        try {
            Cliente cliente = clienteService.asociarArticulos(id, articuloIds);
            return ResponseEntity.ok(cliente);
        } catch (IllegalArgumentException e) {
            ErrorResponses error = new ErrorResponses("Recurso No Encontrado", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error); // 404 Not Found
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    
    @Operation(summary = "Eliminar un Cliente por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Error al obtener el Cliente (No encontrado)", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = ErrorResponses.class))),
            @ApiResponse(responseCode = "500", description = "Error Interno de Servidor", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = ErrorResponses.class)))
        })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(
            @Parameter(description = "Identificador del cliente", example = "1", required = true)
            @PathVariable Long id) {
        try {
            clienteService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}