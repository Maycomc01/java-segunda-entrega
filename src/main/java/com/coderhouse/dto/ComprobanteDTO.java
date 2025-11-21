package com.coderhouse.dto;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComprobanteDTO {

    private ClienteIdDTO cliente; 
    
   
    private List<DetalleVentaDTO> detalleVenta; 
}