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
@Table(name="mot_confort")
public class Yng_MotorizedConfort {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long motorizedConfortId;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "motorized_id")
	@JsonBackReference(value="motorized_id")
    private Yng_Motorized motorized;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "confort_id")	
    private Yng_Confort confort;

	public Yng_MotorizedConfort() {
	
	}

	public Yng_MotorizedConfort(Yng_Motorized motorized, Yng_Confort confort) {
		this.motorized = motorized;
		this.confort = confort;
	}



	public long getMotorizedConfortId() {
		return motorizedConfortId;
	}

	public void setMotorizedConfortId(long motorizedConfortId) {
		this.motorizedConfortId = motorizedConfortId;
	}

	public Yng_Motorized getMotorized() {
		return motorized;
	}

	public void setMotorized(Yng_Motorized motorized) {
		this.motorized = motorized;
	}

	public Yng_Confort getConfort() {
		return confort;
	}

	public void setConfort(Yng_Confort confort) {
		this.confort = confort;
	}

	@Override
	public String toString() {
		return "Yng_MotorizedConfort [motorizedConfortId=" + motorizedConfortId + ", motorized=" + motorized
				+ ", confort=" + confort + "]";
	}


	
	
	
	
	
	

}
