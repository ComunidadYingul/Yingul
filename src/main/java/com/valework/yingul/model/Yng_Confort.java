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
public class Yng_Confort {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "confortId", nullable = false, updatable = false)
    private int confortId;
	private String name;
	
	
	@OneToMany(mappedBy = "confort", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonBackReference
    private Set<Yng_MotorizedConfort> motorizedConfort = new HashSet<>();


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Yng_MotorizedConfort> getMotorizedConfort() {
		return motorizedConfort;
	}

	public void setMotorizedConfort(Set<Yng_MotorizedConfort> motorizedConfort) {
		this.motorizedConfort = motorizedConfort;
	}

	public int getConfortId() {
		return confortId;
	}

	public void setConfortId(int confortId) {
		this.confortId = confortId;
	}

	@Override
	public String toString() {
		return "Yng_Confort [confortId=" + confortId + ", name=" + name + ", motorizedConfort=" + motorizedConfort
				+ "]";
	}


	
    
    
    
    
}
