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
import com.darwinpz.apirest.service.ClienteService;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;
	
	
	//Create
	@PostMapping
	public ResponseEntity<?> create ( @RequestBody Cliente cliente){
		
		try {
			
			return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.save(cliente));
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el cliente: " + e.getMessage());
		}
		
	}
	
	//Read
	@GetMapping("/{id}")
	public ResponseEntity<?> read (@PathVariable(value = "id") Long clienteId){
		
		Optional<Cliente> oCliente = clienteService.findById(clienteId);
		
		if(!oCliente.isPresent()) return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok(oCliente);
	
	}
	
	//Update
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@RequestBody Cliente cliente,@PathVariable(value = "id") Long clienteId){
	
		try {
			
			Optional<Cliente> oCliente = clienteService.findById(clienteId);
			if(!oCliente.isPresent()) return ResponseEntity.notFound().build();
			
			oCliente.get().setIdentificacion(cliente.getIdentificacion());
			oCliente.get().setNombre(cliente.getNombre());
			oCliente.get().setGenero(cliente.getGenero());
			oCliente.get().setEdad(cliente.getEdad());
			oCliente.get().setDireccion(cliente.getDireccion());
			oCliente.get().setTelefono(cliente.getTelefono());
			oCliente.get().setEstado(cliente.getEstado());
			oCliente.get().setClave(cliente.getClave());
			
			clienteService.save(oCliente.get());
			return ResponseEntity.status(HttpStatus.CREATED).body("Cliente actualizado correctamente");
		
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el cliente: " + e.getMessage());
		}
	}
	
	//Delete
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete (@PathVariable(value = "id") Long clienteId){
		
		try {
			
			if(!clienteService.findById(clienteId).isPresent()) return ResponseEntity.notFound().build();
			clienteService.deleteById(clienteId);
			return ResponseEntity.ok().body("Cliente eliminado correctamente");
			
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el cliente: " + e.getMessage());
		}
	
	}	
	
	
	//Read All
	@GetMapping
	public List<Cliente> readAll (){
		
		return StreamSupport.stream(clienteService.findAll().spliterator(), false).collect(Collectors.toList());
	
	}	

	
}
