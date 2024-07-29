package com.darwinpz.apirest.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.darwinpz.apirest.entity.Cuenta;


@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long>{

	Optional<Cuenta> findByNumeroCuenta(int numeroCuenta);
	
}
