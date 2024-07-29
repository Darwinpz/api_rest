package com.darwinpz.apirest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.darwinpz.apirest.entity.Cliente;
import com.darwinpz.apirest.entity.Cuenta;

public interface CuentaService {

	public Iterable<Cuenta> findAll();
	public Page<Cuenta> findAll(Pageable pageable);
	public Optional<Cuenta> findById(Long cuentaId);
	public Cuenta save(Cuenta cuenta);
	public void deleteById(Long id);
	public Optional<Cuenta> findByNumeroCuenta(int numeroCuenta);
	public List<Cuenta> findByCliente(Cliente cliente);
	
}
