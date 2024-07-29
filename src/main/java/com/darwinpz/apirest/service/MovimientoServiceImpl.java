package com.darwinpz.apirest.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.darwinpz.apirest.entity.Movimiento;
import com.darwinpz.apirest.repository.MovimientoRepository;

@Service
public class MovimientoServiceImpl implements MovimientoService{

	@Autowired
	private MovimientoRepository movimientoRepository;
	
	@Override
	@Transactional(readOnly = true)
	public Iterable<Movimiento> findAll() {
		return movimientoRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Movimiento> findAll(Pageable pageable) {
		return movimientoRepository.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Movimiento> findById(Long id) {
		return movimientoRepository.findById(id);
	}

	@Override
	@Transactional
	public Movimiento save(Movimiento movimiento) {
		return movimientoRepository.save(movimiento);
	}
	

	@Override
	@Transactional
	public void deleteById(Long id) {
		movimientoRepository.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Movimiento> findTopByCuentaIdOrderByFechaDesc(Long cuentaId) {
		return movimientoRepository.findTopByCuentaIdOrderByFechaDesc(cuentaId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Movimiento> findByClienteIdAndFechaBetween(Long clienteId, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
		return movimientoRepository.findByClienteIdAndFechaBetween(clienteId, fechaInicio, fechaFin);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Movimiento> findByClienteIdentAndFechaBetween(String identificacion, LocalDateTime fechaInicio,
			LocalDateTime fechaFin) {
		return movimientoRepository.findByClienteIdentAndFechaBetween(identificacion, fechaInicio, fechaFin);
	}

	

}
