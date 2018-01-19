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
public class Yng_Equipment {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "equipmentId", nullable = false, updatable = false)
    private int equipmentId;
	private String name;

	
	@OneToMany(mappedBy = "equipment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonBackReference
    private Set<Yng_MotorizedEquipment> motorizedEquipment = new HashSet<>();


	public int getEquipmentId() {
		return equipmentId;
	}


	public void setEquipmentId(int equipmentId) {
		this.equipmentId = equipmentId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Set<Yng_MotorizedEquipment> getMotorizedEquipment() {
		return motorizedEquipment;
	}


	public void setMotorizedEquipment(Set<Yng_MotorizedEquipment> motorizedEquipment) {
		this.motorizedEquipment = motorizedEquipment;
	}


	@Override
	public String toString() {
		return "Yng_Equipment [equipmentId=" + equipmentId + ", name=" + name + ", motorizedEquipment="
				+ motorizedEquipment + "]";
	}
    
    
}
