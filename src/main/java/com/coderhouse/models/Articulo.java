package com.coderhouse.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Modelo de Artículo (Producto) disponible en stock")
@Table(name = "Articulos")
public class Articulo {

	@Schema(description = "ID único del Articulo", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Schema(description = "Nombre del Articulo (producto)", requiredMode = Schema.RequiredMode.REQUIRED, example = "Mochila kid")
	@Column(name = "Nombre", nullable = false)
	private String nombre;
	
	@Schema(description = "Precio actual de venta del Artículo", requiredMode = Schema.RequiredMode.REQUIRED, example = "250.50")
	@Column(name = "Precio", nullable = false) 
    private Double precio;
	
	@Schema(description = "Stock de unidades disponibles", requiredMode = Schema.RequiredMode.REQUIRED, example = "50")
	@Column(name="Stock", nullable=false)
	private Integer stock;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "articulos_cliente", 
		joinColumns = @JoinColumn(name = "articulo_id"), 
		inverseJoinColumns = @JoinColumn(name = "cliente_id"))
	@JsonIgnore
	private List<Cliente> clientes = new ArrayList<>();


}