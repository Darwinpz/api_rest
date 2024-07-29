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

import com.darwinpz.apirest.entity.Cliente;
import com.darwinpz.apirest.entity.Cuenta;
import com.darwinpz.apirest.service.ClienteService;
import com.darwinpz.apirest.service.CuentaService;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {

	@Autowired
	private CuentaService cuentaService;
	
	@Autowired
	private ClienteService clienteService;

	//Create
	@PostMapping
	public ResponseEntity<?> create (@RequestBody Cuenta cuenta){
		
		try {
			
			if (cuenta.getCliente() == null) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cliente no proporcionado.");
	        }

	        Optional<Cliente> oCliente = Optional.ofNullable(cuenta.getCliente().getId() != null
	                ? clienteService.findById(cuenta.getCliente().getId()).orElse(null)
	                : clienteService.findByIdentificacion(cuenta.getCliente().getIdentificacion()).orElse(null));

	        if (oCliente.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado.");
	        }

	        cuenta.setCliente(oCliente.get());

	        if (cuenta.getSaldoInicial() < 0) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El saldo inicial no puede ser negativo");
	        }
			
			cuentaService.save(cuenta);
			return ResponseEntity.status(HttpStatus.CREATED).body("Cuenta creada correctamente");
			
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la cuenta: " + e.getMessage());
		}
		
	}
	
	//Read
	@GetMapping("/{id}")
	public ResponseEntity<?> read (@PathVariable(value = "id") Long numeroCuenta){
		
		Optional<Cuenta> oCuenta = cuentaService.findById(numeroCuenta);
		
		if(!oCuenta.isPresent()) return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok(oCuenta);
	
	}
	
	//Update
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@RequestBody Cuenta cuenta,@PathVariable(value = "id") Long numeroCuenta){
	
		try {
			
			Optional<Cuenta> oCuenta = cuentaService.findById(numeroCuenta);
			if(!oCuenta.isPresent()) return ResponseEntity.notFound().build();
			
			oCuenta.get().setTipoCuenta(cuenta.getTipoCuenta());
			oCuenta.get().setEstado(cuenta.getEstado());
			cuentaService.save(oCuenta.get());
			return ResponseEntity.status(HttpStatus.CREATED).body("Cuenta actualizada correctamente");
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la cuenta: " + e.getMessage());
		}
	}
	

	//Delete
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete (@PathVariable(value = "id") Long numeroCuenta){
		
		try {
			
			if(!cuentaService.findById(numeroCuenta).isPresent()) return ResponseEntity.notFound().build();
			cuentaService.deleteById(numeroCuenta);
			return ResponseEntity.ok().body("Cuenta eliminada correctamente");
		
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la cuenta: " + e.getMessage());
		}
	
	}	
	
	//Read All
	@GetMapping
	public List<Cuenta> readAll (){
		
		return StreamSupport.stream(cuentaService.findAll().spliterator(), false).collect(Collectors.toList());
	
	}	

	
}
