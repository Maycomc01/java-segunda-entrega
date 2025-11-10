package com.coderhouse.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
import lombok.Data;


@Data
@Entity
@Table(name = "Articulos")
public class Articulo {

	@Id // Primary Key
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "Nombre", nullable = false)
	private String nombre;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "articulos_cliente", 
		joinColumns = @JoinColumn(name = "articulo_id"), 
		inverseJoinColumns = @JoinColumn(name = "cliente_id"))
	@JsonIgnore
	private List<Cliente> clientes = new ArrayList<>();


}