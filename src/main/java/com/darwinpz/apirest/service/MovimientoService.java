package com.darwinpz.apirest.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.darwinpz.apirest.entity.Movimiento;

public interface MovimientoService {
	
	public Iterable<Movimiento> findAll();
	public Page<Movimiento> findAll(Pageable pageable);
	public Optional<Movimiento> findById(Long id);
	public Movimiento save(Movimiento movimiento);
	public void deleteById(Long id);
	public Optional<Movimiento> findTopByCuentaIdOrderByFechaDesc(Long cuentaId);
	public List<Movimiento> findByClienteIdAndFechaBetween(Long clienteId, LocalDateTime fechaInicio, LocalDateTime fechaFin);
	public List<Movimiento> findByClienteIdentAndFechaBetween(String identificacion, LocalDateTime fechaInicio, LocalDateTime fechaFin);

}
