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
@Table(name="mot_Exterior")
public class Yng_MotorizedExterior {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long motorizedExteriorId;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "motorized_id")
	@JsonBackReference(value="motorized_id")
    private Yng_Motorized motorized;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exterior_id")	
    private Yng_Exterior exterior;

	public Yng_MotorizedExterior() {
		
	}

	public Yng_MotorizedExterior(Yng_Motorized motorized, Yng_Exterior exterior) {
		
		this.motorized = motorized;
		this.exterior = exterior;
	}

	public long getMotorizedExteriorId() {
		return motorizedExteriorId;
	}

	public void setMotorizedExteriorId(long motorizedExteriorId) {
		this.motorizedExteriorId = motorizedExteriorId;
	}

	public Yng_Motorized getMotorized() {
		return motorized;
	}

	public void setMotorized(Yng_Motorized motorized) {
		this.motorized = motorized;
	}

	public Yng_Exterior getExterior() {
		return exterior;
	}

	public void setExterior(Yng_Exterior exterior) {
		this.exterior = exterior;
	}

	@Override
	public String toString() {
		return "Yng_MotorizedExterior [motorizedExteriorId=" + motorizedExteriorId + ", motorized=" + motorized
				+ ", exterior=" + exterior + "]";
	}	
	
}
