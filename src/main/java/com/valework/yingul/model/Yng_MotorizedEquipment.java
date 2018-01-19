package com.valework.yingul.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="mot_equipment")
public class Yng_MotorizedEquipment {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long motorizedEquipmentId;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "motorized_id")
	@JsonBackReference(value="motorized_id")
    private Yng_Motorized motorized;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "equipment_id")	
    private Yng_Equipment equipment;

	public Yng_MotorizedEquipment() {
	
	}



	public Yng_Equipment getEquipment() {
		return equipment;
	}



	public void setEquipment(Yng_Equipment equipment) {
		this.equipment = equipment;
	}



	@Override
	public String toString() {
		return "Yng_MotorizedEquipment [motorizedEquipmentId=" + motorizedEquipmentId + ", motorized=" + motorized
				+ ", equipment=" + equipment + "]";
	}



	public long getMotorizedEquipmentId() {
		return motorizedEquipmentId;
	}

	public void setMotorizedEquipmentId(long motorizedEquipmentId) {
		this.motorizedEquipmentId = motorizedEquipmentId;
	}

	public Yng_Motorized getMotorized() {
		return motorized;
	}

	public void setMotorized(Yng_Motorized motorized) {
		this.motorized = motorized;
	}


	
	

}
