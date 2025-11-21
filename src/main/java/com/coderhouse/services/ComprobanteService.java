package com.coderhouse.services;

import com.coderhouse.dto.ComprobanteDTO;
import com.coderhouse.dto.DetalleVentaDTO;
import com.coderhouse.models.Articulo;
import com.coderhouse.models.Cliente;
import com.coderhouse.models.Comprobante;
import com.coderhouse.models.DetalleVenta;
import com.coderhouse.repositories.ArticuloRepository;
import com.coderhouse.repositories.ClienteRepository;
import com.coderhouse.repositories.ComprobanteRepository;
import com.coderhouse.repositories.DetalleVentaRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ComprobanteService {
    

    private final ClienteRepository clienteRepository;
    private final ArticuloRepository articuloRepository;
    private final ComprobanteRepository comprobanteRepository;
    private final DetalleVentaRepository detalleVentaRepository;
    
    
    public ComprobanteService(
        ClienteRepository clienteRepository, 
        ArticuloRepository articuloRepository, 
        ComprobanteRepository comprobanteRepository,
        DetalleVentaRepository detalleVentaRepository) {
        this.clienteRepository = clienteRepository;
        this.articuloRepository = articuloRepository;
        this.comprobanteRepository = comprobanteRepository;
        this.detalleVentaRepository = detalleVentaRepository;
    }
    
    
    
    public List<Comprobante> findAll() {
        return comprobanteRepository.findAll();
    }

    
   
    public Comprobante findById(Long id) {
        return comprobanteRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException(
                "Comprobante con ID " + id + " no encontrado."
            ));
    }
    
    
    
    @Transactional
    public Comprobante crearComprobante(ComprobanteDTO request) {
        
        List<Articulo> articulosParaActualizar = new ArrayList<>();
        List<DetalleVenta> detallesParaGuardar = new ArrayList<>();
        Double totalCalculado = 0.0;
        Integer cantidadTotalArticulos = 0; 
        

        Long clienteId = request.getCliente().getClienteid();
        Cliente cliente = clienteRepository.findById(clienteId)
            .orElseThrow(() -> new IllegalArgumentException( // 404 Not Found
                "Cliente con ID " + clienteId + " no encontrado."
            ));

        
        for (DetalleVentaDTO detalleDTO : request.getDetalleVenta()) {
            Long articuloId = detalleDTO.getArticulo().getProductoid();
            Integer cantidadSolicitada = detalleDTO.getCantidad();
            
            Articulo articulo = articuloRepository.findById(articuloId)
                .orElseThrow(() -> new IllegalArgumentException( // 404 Not Found
                    "Artículo con ID " + articuloId + " no encontrado."
                ));
            
            
            if (cantidadSolicitada > articulo.getStock()) {
                throw new IllegalStateException("Stock insuficiente para el artículo ID " + articuloId + 
                    ". Solicitado: " + cantidadSolicitada + ", Disponible: " + articulo.getStock()); //409 CONFLICT
            }
            
            
            Double precioUnitario = articulo.getPrecio();
            Double subtotal = precioUnitario * cantidadSolicitada;
            
            totalCalculado += subtotal;
            cantidadTotalArticulos += cantidadSolicitada;
            
            articulo.setStock(articulo.getStock() - cantidadSolicitada);
            articulosParaActualizar.add(articulo);
            
            DetalleVenta detalle = new DetalleVenta();
            detalle.setArticulo(articulo);
            detalle.setCantidad(cantidadSolicitada);
            detalle.setPrecioUnitario(precioUnitario); 
            detalle.setSubtotal(subtotal);
            detallesParaGuardar.add(detalle);
        }

      
        LocalDateTime fechaComprobante = LocalDateTime.now(); 
        
        
        Comprobante comprobante = new Comprobante();
        comprobante.setCliente(cliente);
        comprobante.setFecha(fechaComprobante);
        comprobante.setTotal(totalCalculado);
        comprobante.setCantidadArticulos(cantidadTotalArticulos); 
        
        comprobante = comprobanteRepository.save(comprobante);

        
        for(DetalleVenta detalle : detallesParaGuardar) {
            detalle.setComprobante(comprobante);
        }
        detalleVentaRepository.saveAll(detallesParaGuardar);
        
        articuloRepository.saveAll(articulosParaActualizar); 

        comprobante.setDetalleVenta(detallesParaGuardar);
        return comprobante; 
    }
}