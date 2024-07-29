package com.darwinpz.apirest.controller;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.darwinpz.apirest.dto.ReporteDTO;
import com.darwinpz.apirest.entity.Movimiento;
import com.darwinpz.apirest.service.MovimientoService;

@RestController
@RequestMapping("/api/reportes")
public class ReportesController {

	@Autowired
	private MovimientoService movimientoService;
	
	
	//Read
	@GetMapping
	public ResponseEntity<?> reportebyId (@RequestParam(required = false) Long clienteId,
			@RequestParam(required = false) String identificacion,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin){
		
		try {
            
            LocalDateTime fechaInicioDateTime = fechaInicio.atStartOfDay();
            LocalDateTime fechaFinDateTime = fechaFin.atTime(23, 59, 59);
            
            List<Movimiento> movimientos; 

            if (clienteId != null) {
                movimientos = movimientoService.findByClienteIdAndFechaBetween(clienteId, fechaInicioDateTime, fechaFinDateTime);
            } else if (identificacion != null) {
                movimientos = movimientoService.findByClienteIdentAndFechaBetween(identificacion, fechaInicioDateTime, fechaFinDateTime);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Debe proporcionar el Id del cliente o la identificación.");
            }
            
            List<ReporteDTO> reporte = movimientos.stream().map(mov -> {
                ReporteDTO dto = new ReporteDTO();
                dto.setFecha(mov.getFecha().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
                dto.setCliente(mov.getCuenta().getCliente().getNombre());
                dto.setNumeroCuenta(mov.getCuenta().getNumeroCuenta());
                dto.setTipo(mov.getCuenta().getTipoCuenta());
                dto.setSaldoInicial(mov.getCuenta().getSaldoInicial());
                dto.setEstado(mov.getCuenta().getEstado());
                dto.setMovimiento(mov.getValor());
                dto.setSaldoDisponible(mov.getSaldo());
                return dto;
            }).collect(Collectors.toList());
            
            return ResponseEntity.ok(reporte);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al generar el reporte: " + e.getMessage());
        }
	
	}

	
}
