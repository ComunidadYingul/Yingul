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
public class Yng_Exterior {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "exteriorId", nullable = false, updatable = false)
    private int exteriorId;
	private String name;
	
	
	@OneToMany(mappedBy = "exterior", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonBackReference
    private Set<Yng_MotorizedExterior> motorizedExterior = new HashSet<>();


	public int getExteriorId() {
		return exteriorId;
	}


	public void setExteriorId(int exteriorId) {
		this.exteriorId = exteriorId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Set<Yng_MotorizedExterior> getMotorizedExterior() {
		return motorizedExterior;
	}


	public void setMotorizedExterior(Set<Yng_MotorizedExterior> motorizedExterior) {
		this.motorizedExterior = motorizedExterior;
	}


	@Override
	public String toString() {
		return "Yng_Exterior [exteriorId=" + exteriorId + ", name=" + name + ", motorizedExterior=" + motorizedExterior
				+ "]";
	}
    
    
	


}
