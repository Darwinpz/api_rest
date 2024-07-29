package com.darwinpz.apirest.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.darwinpz.apirest.entity.Cuenta;
import com.darwinpz.apirest.entity.Movimiento;
import com.darwinpz.apirest.service.CuentaService;
import com.darwinpz.apirest.service.MovimientoService;

@RestController
@RequestMapping("/api/movimientos")
public class MovimientoController {

	@Autowired
	private MovimientoService movimientoService;
	@Autowired
	private CuentaService cuentaService;
	
	//Create
	@PostMapping
	public ResponseEntity<?> create (@RequestBody Movimiento movimiento){
		
		try {
			
			if (movimiento.getCuenta() == null) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cuenta no proporcionada.");
	        }

	        Optional<Cuenta> oCuenta = Optional.ofNullable(movimiento.getCuenta().getId() != null
	                ? cuentaService.findById(movimiento.getCuenta().getId()).orElse(null)
	                : cuentaService.findByNumeroCuenta(movimiento.getCuenta().getNumeroCuenta()).orElse(null));

	        if (oCuenta.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cuenta no encontrada.");
	        }

	        Cuenta cuenta = oCuenta.get();
	        movimiento.setCuenta(cuenta);

	        Double saldoDisponible = movimientoService.findTopByCuentaIdOrderByFechaDesc(cuenta.getId())
	                .map(Movimiento::getSaldo)
	                .orElse(cuenta.getSaldoInicial());

	        if (movimiento.getValor() == 0) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El valor del movimiento no puede ser cero.");
	        }

	        if (movimiento.getValor() < 0 && saldoDisponible + movimiento.getValor() < 0) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Saldo insuficiente para realizar el retiro.");
	        }

	        movimiento.setTipoMovimiento(movimiento.getValor() > 0
	                ? String.format("Depósito de %.2f", movimiento.getValor())
	                : String.format("Retiro de %.2f", movimiento.getValor()));
	        movimiento.setSaldo(saldoDisponible + movimiento.getValor());
	        
	        movimientoService.save(movimiento);
			return ResponseEntity.status(HttpStatus.CREATED).body("Movimiento creado correctamente");
			
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el movimiento: " + e.getMessage());
		}
		
	}
	
	//Read
	@GetMapping("/{id}")
	public ResponseEntity<?> read (@PathVariable(value = "id") Long movimientoId){
		
		Optional<Movimiento> oMovimiento = movimientoService.findById(movimientoId);
		
		if(!oMovimiento.isPresent()) return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok(oMovimiento);
	
	}
	
	//Update
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@RequestBody Movimiento movimiento,@PathVariable(value = "id") Long movimientoId){
	
		try {
			
			Optional<Movimiento> oMovimiento = movimientoService.findById(movimientoId);
			if(!oMovimiento.isPresent()) return ResponseEntity.notFound().build();
			
			oMovimiento.get().setTipoMovimiento(movimiento.getTipoMovimiento());
			oMovimiento.get().setValor(movimiento.getValor());
			movimientoService.save(oMovimiento.get());
			return ResponseEntity.status(HttpStatus.CREATED).body("Movimiento actualizado correctamente");
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el movimiento: " + e.getMessage());
		}
	}
	

	//Delete
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete (@PathVariable(value = "id") Long movimientoId){
		
		try {
			
			if(!movimientoService.findById(movimientoId).isPresent()) return ResponseEntity.notFound().build();
			movimientoService.deleteById(movimientoId);
			return ResponseEntity.ok().body("Movimiento eliminado correctamente");
		
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el movimiento: " + e.getMessage());
		}
	
	}	
	
	
	//Read All
	@GetMapping
	public List<Movimiento> readAll (){
		
		return StreamSupport.stream(movimientoService.findAll().spliterator(), false).collect(Collectors.toList());
	
	}	

	
}
