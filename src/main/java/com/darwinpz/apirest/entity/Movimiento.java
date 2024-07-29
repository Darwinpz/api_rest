package com.darwinpz.apirest.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "movimiento")
public class Movimiento {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movimientoId;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime fecha;

    @Column(nullable = false)
    private String tipoMovimiento;
    
    @Column(nullable = false)
    private double valor;
    
    @Column(nullable = false)
    private double saldo;
    
    @ManyToOne
    private Cuenta cuenta;

    // Constructor
    public Movimiento() {
    }

    // Getters y Setters
    public Long getmovimientoId() {
        return movimientoId;
    }

    public void setmovimientoId(Long movimientoId) {
        this.movimientoId = movimientoId;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
	
    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }
    
}
