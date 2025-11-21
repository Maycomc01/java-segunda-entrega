package com.coderhouse.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table; 
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Detalle de un artículo dentro de un Comprobante de Venta")
@Table(name = "DetallesVenta") 
public class DetalleVenta {
    
	@Schema(description = "ID del Detalle de Venta", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 
    
	@Schema(description = "Cantidad de unidades vendidas", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @Column(nullable = false)
    private Integer cantidad;
    
	
	@Schema(description = "Precio unitario del artículo al momento de la venta", requiredMode = Schema.RequiredMode.REQUIRED, example = "100.00")
    @Column(nullable = false)
    private Double precioUnitario; 
    
	
	@Schema(description = "Subtotal (cantidad * precioUnitario)", requiredMode = Schema.RequiredMode.REQUIRED, example = "300.00")
    @Column(nullable = false)
    private Double subtotal; 
    
	@Schema(description = "Referencia al Comprobante padre")
    @ManyToOne 
    @JoinColumn(name = "comprobante_id", nullable = false)
	@JsonIgnore
    private Comprobante comprobante;
    
	
	@Schema(description = "Referencia al Artículo vendido")
    @ManyToOne
    @JoinColumn(name = "articulo_id", nullable = false)
    private Articulo articulo;
    
}