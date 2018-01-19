package com.valework.yingul.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Yng_Security {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "securityId", nullable = false, updatable = false)
    private int securityId;
	private String name;
	private String descripcion;
	
	@OneToMany(mappedBy = "security", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonBackReference
    private Set<Yng_MotorizedSecurity> motorizedSecurity = new HashSet<>();
    
    
    

	public int getSecurityId() {
		return securityId;
	}

	public void setSecurityId(int securityId) {
		this.securityId = securityId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Set<Yng_MotorizedSecurity> getMotorizedSecurity() {
		return motorizedSecurity;
	}

	public void setMotorizedSecurity(Set<Yng_MotorizedSecurity> motorizedSecurity) {
		this.motorizedSecurity = motorizedSecurity;
	}

	@Override
	public String toString() {
		return "Yng_Security [securityId=" + securityId + ", name=" + name + ", descripcion=" + descripcion
				+ ", motorizedSecurity=" + motorizedSecurity + "]";
	}

}
