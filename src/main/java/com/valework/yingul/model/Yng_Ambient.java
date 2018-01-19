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
public class Yng_Ambient {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ambientId", nullable = false, updatable = false)
    private int ambientId;
	private String name;

	
	@OneToMany(mappedBy = "ambient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonBackReference
    private Set<Yng_PropertyAmbient> propetyAmbient = new HashSet<>();


	public int getAmbientId() {
		return ambientId;
	}


	public void setAmbientId(int ambientId) {
		this.ambientId = ambientId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Set<Yng_PropertyAmbient> getPropetyAmbient() {
		return propetyAmbient;
	}


	public void setPropetyAmbient(Set<Yng_PropertyAmbient> propetyAmbient) {
		this.propetyAmbient = propetyAmbient;
	}


	@Override
	public String toString() {
		return "Yng_Ambient [ambientId=" + ambientId + ", name=" + name + ", propetyAmbient=" + propetyAmbient + "]";
	}
    
    
    
    

}
