package com.coderhouse.models;

import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description="Modelo de cliente")
@Table(name = "Cliente")
public class Cliente {

	@Schema(description = "ID único del Cliente", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cliente_id") 
	private Long id;

	@Schema(description = "Nombre del Cliente", requiredMode = Schema.RequiredMode.REQUIRED, example = "Adam")
	@Column(name = "Nombre", nullable = false)
	private String nombre;
	
	@Schema(description = "Apellido del Cliente", requiredMode = Schema.RequiredMode.REQUIRED, example = "Smith")
	@Column(name = "Apellido", nullable = false)
	private String apellido;
	
	@Schema(description = "DNI del Cliente", requiredMode = Schema.RequiredMode.REQUIRED, example = "30123456")
	@Column(name = "DNI", nullable = false, unique = true)
	private int dni;
	
	@Schema(description = "E-mail del Cliente", requiredMode = Schema.RequiredMode.REQUIRED, example = "adamsmith@gmail.com")
	@Column(name = "Email", nullable = false, unique = true)
	private String email;


	@Schema(description = "Relación con la lista de Artículos comprados")
	@ManyToMany(mappedBy = "clientes", fetch = FetchType.EAGER)
	private List<Articulo> articulos = new ArrayList<>();
}