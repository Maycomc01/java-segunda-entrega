package com.coderhouse.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Cliente")
public class Cliente {

	@Id // Primary Key
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "Nombre", nullable = false)
	private String nombre;

	@Column(name = "Apellido", nullable = false)
	private String apellido;

	@Column(name = "DNI", nullable = false, unique = true)
	private int dni;

	@Column(name = "Email", nullable = false, unique = true)
	private String email;

	@Column(name = "Edad")
	private int edad;


	@ManyToMany(mappedBy = "clientes", fetch = FetchType.EAGER)
	private List<Articulo> articulos = new ArrayList<>();
}