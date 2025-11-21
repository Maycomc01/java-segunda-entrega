package com.coderhouse.models;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column; 
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Table; 

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Modelo de Comprobante (Venta)")
@Table(name = "Comprobantes") 
public class Comprobante {
    
	
	@Schema(description = "ID único del Comprobante generado", requiredMode = Schema.RequiredMode.REQUIRED, example = "1001")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comprobanteid;
    
	
	@Schema(description = "Entidad Cliente que realizó la compra")
    @ManyToOne 
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente; 
    
	
	@Schema(description = "Detalle de los Artículos y cantidades vendidas")
    @OneToMany(mappedBy = "comprobante")
  
	@JsonIgnore
    private List<DetalleVenta> detalleVenta; 
    
	
	@Schema(description = "Fecha y hora de la venta", requiredMode = Schema.RequiredMode.REQUIRED, example = "2025-11-18T14:30:00")
    @Column(name="Fecha",nullable = false)
    private LocalDateTime fecha;
    
	
	@Schema(description = "Monto total del Comprobante", requiredMode = Schema.RequiredMode.REQUIRED, example = "1500.75")
    @Column(name="Total",nullable = false)
    private Double total;
    
	
	@Schema(description = "Cantidad total de unidades de Artículos vendidas", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    @Column(name="Cantidad", nullable = false)
    private Integer cantidadArticulos;
}