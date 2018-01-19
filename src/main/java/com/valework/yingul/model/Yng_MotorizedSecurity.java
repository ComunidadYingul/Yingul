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
@Table(name="mot_security")
public class Yng_MotorizedSecurity {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long motorizedSecurityId;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "motorized_id")
	@JsonBackReference(value="motorized_id")
    private Yng_Motorized motorized;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "security_id")	
    private Yng_Security security;
	

	
	public Yng_MotorizedSecurity() {
	}
	public Yng_MotorizedSecurity(Yng_Motorized motorized, Yng_Security security) {
		this.motorized = motorized;
		this.security = security;
	}

	public long getMotorizedSecurityId() {
		return motorizedSecurityId;
	}

	public void setMotorizedSecurityId(long motorizedSecurityId) {
		this.motorizedSecurityId = motorizedSecurityId;
	}

	public Yng_Motorized getMotorized() {
		return motorized;
	}

	public void setMotorized(Yng_Motorized motorized) {
		this.motorized = motorized;
	}

	public Yng_Security getSecurity() {
		return security;
	}

	public void setSecurity(Yng_Security security) {
		this.security = security;
	}

	@Override
	public String toString() {
		return "Yng_MotorizedSecurity [motorizedSecurityId=" + motorizedSecurityId + ", motorized=" + motorized
				+ ", security=" + security + "]";
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
