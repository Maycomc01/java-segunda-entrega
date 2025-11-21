package com.coderhouse.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
@Schema( description="DTO de Clientes")
public class ClienteIdDTO {
	@Schema(description="ID de Cleintes", example="1")
    private Long clienteid; 
}