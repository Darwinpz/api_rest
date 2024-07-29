package com.darwinpz.apirest.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Cliente extends Persona{

	@NotBlank
	@Column(nullable = false, length = 20)
    private String clave;
    
	@NotNull
    @Column(nullable = false)
    private boolean estado;
    
    @OneToMany(mappedBy = "cliente")
    private List<Cuenta> cuentas;
	
	// Constructor
    public Cliente() {
        super();
    }

    // Getters y Setters
    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
	
}
