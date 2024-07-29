package com.darwinpz.apirest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.darwinpz.apirest.entity.Cliente;
import com.darwinpz.apirest.entity.Cuenta;
import com.darwinpz.apirest.repository.CuentaRepository;

@Service
public class CuentaServiceImpl implements CuentaService{

	@Autowired
	private CuentaRepository cuentaRepository;
	
	@Override
	@Transactional(readOnly = true)
	public Iterable<Cuenta> findAll() {
		return cuentaRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Cuenta> findAll(Pageable pageable) {
		return cuentaRepository.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Cuenta> findById(Long cuentaId) {
		return cuentaRepository.findById(cuentaId);
	}

	@Override
	@Transactional
	public Cuenta save(Cuenta cuenta) {
		return cuentaRepository.save(cuenta);
	}

	@Override
	@Transactional
	public void deleteById(Long numeroCuenta) {
		cuentaRepository.deleteById(numeroCuenta);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Cuenta> findByNumeroCuenta(int numeroCuenta) {
		return cuentaRepository.findByNumeroCuenta(numeroCuenta);
	}

	@Override
	public List<Cuenta> findByCliente(Cliente cliente) {
		return cuentaRepository.findByCliente(cliente);
	}


		


}
