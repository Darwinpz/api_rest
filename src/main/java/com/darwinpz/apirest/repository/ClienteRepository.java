package com.darwinpz.apirest.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.darwinpz.apirest.entity.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{

	
	Optional<Cliente> findByIdentificacion(String identificacion);
	
}
