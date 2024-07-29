package com.darwinpz.apirest.repository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.darwinpz.apirest.entity.Movimiento;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long>{

	Optional<Movimiento> findTopByCuentaIdOrderByFechaDesc(Long cuentaId);
	
	@Query("SELECT m FROM Movimiento m WHERE m.cuenta.cliente.id = :clienteId AND m.fecha BETWEEN :fechaInicio AND :fechaFin")
	List<Movimiento> findByClienteIdAndFechaBetween(@Param("clienteId") Long clienteId, @Param("fechaInicio") LocalDateTime fechaInicio,@Param("fechaFin") LocalDateTime fechaFin);
	
	@Query("SELECT m FROM Movimiento m WHERE m.cuenta.cliente.identificacion = :identificacion AND m.fecha BETWEEN :fechaInicio AND :fechaFin")
	List<Movimiento> findByClienteIdentAndFechaBetween(@Param("identificacion") String identificacion, @Param("fechaInicio") LocalDateTime fechaInicio,@Param("fechaFin") LocalDateTime fechaFin);
	
}
