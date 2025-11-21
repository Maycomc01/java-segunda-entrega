package com.coderhouse.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema( description="DTO de Articulos")
public class ArticuloIdDTO {	
	@Schema(description="ID de Articulo", example="1")
	 private Long productoid; 

}
