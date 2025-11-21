package com.coderhouse.dto;

import lombok.Data;


@Data
public class DetalleVentaDTO { 
    
    private Integer cantidad;
    private ArticuloIdDTO articulo; 
}