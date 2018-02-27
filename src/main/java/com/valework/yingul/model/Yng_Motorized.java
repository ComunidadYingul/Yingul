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
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


@Entity
public class Yng_Motorized {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "motorizedId", nullable = false, updatable = false)
    private Long motorizedId;
	
	@Column(name="motorizedBrand")
	private String motorizedBrand;
	@Column(name="motorizedYear")
	private String motorizedYear;
	@Column(name="motorizedModel")	
	private String motorizedModel;
	@Column(name="motorizedUnicoDue")	
	private String motorizedUnicoDue;
	private String condition;
	
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "item_id")
	private Yng_Item yng_Item;
	
	@OneToMany(mappedBy = "motorized", cascade = CascadeType.ALL, fetch = FetchType.LAZY)  
    private Set<Yng_MotorizedSecurity> motorizedSecurity = new HashSet<>();
    
    @OneToMany(mappedBy= "motorized", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Yng_MotorizedConfort> motorizedConfort =new HashSet<>();
    
    @OneToMany(mappedBy = "motorized", cascade = CascadeType.ALL, fetch = FetchType.LAZY)  
    private Set<Yng_MotorizedEquipment> motorizedEquipment = new HashSet<>();
    
    @OneToMany(mappedBy = "motorized", cascade = CascadeType.ALL, fetch = FetchType.LAZY)  
    private Set<Yng_MotorizedExterior> motorizedExterior = new HashSet<>();
    
	@OneToMany(mappedBy = "motorized", cascade = CascadeType.ALL, fetch = FetchType.LAZY)  
    private Set<Yng_MotorizedSound> motorizedSound = new HashSet<>();
      


	public Long getMotorizedId() {
		return motorizedId;
	}

	public void setMotorizedId(Long motorizedId) {
		this.motorizedId = motorizedId;
	}

	public String getMotorizedBrand() {
		return motorizedBrand;
	}

	public void setMotorizedBrand(String motorizedBrand) {
		this.motorizedBrand = motorizedBrand;
	}

	public String getMotorizedYear() {
		return motorizedYear;
	}

	public void setMotorizedYear(String motorizedYear) {
		this.motorizedYear = motorizedYear;
	}

	public String getMotorizedModel() {
		return motorizedModel;
	}

	public void setMotorizedModel(String motorizedModel) {
		this.motorizedModel = motorizedModel;
	}

	public String getMotorizedUnicoDue() {
		return motorizedUnicoDue;
	}

	public void setMotorizedUnicoDue(String motorizedUnicoDue) {
		this.motorizedUnicoDue = motorizedUnicoDue;
	}

	public Yng_Item getYng_Item() {
		return yng_Item;
	}

	public void setYng_Item(Yng_Item yng_Item) {
		this.yng_Item = yng_Item;
	}

	public Set<Yng_MotorizedSecurity> getMotorizedSecurity() {
		return motorizedSecurity;
	}

	public void setMotorizedSecurity(Set<Yng_MotorizedSecurity> motorizedSecurity) {
		this.motorizedSecurity = motorizedSecurity;
	}

	public Set<Yng_MotorizedConfort> getMotorizedConfort() {
		return motorizedConfort;
	}

	public void setMotorizedConfort(Set<Yng_MotorizedConfort> motorizedConfort) {
		this.motorizedConfort = motorizedConfort;
	}



	public Set<Yng_MotorizedSound> getMotorizedSound() {
		return motorizedSound;
	}

	public void setMotorizedSound(Set<Yng_MotorizedSound> motorizedSound) {
		this.motorizedSound = motorizedSound;
	}

	public Set<Yng_MotorizedEquipment> getMotorizedEquipment() {
		return motorizedEquipment;
	}

	public void setMotorizedEquipment(Set<Yng_MotorizedEquipment> motorizedEquipment) {
		this.motorizedEquipment = motorizedEquipment;
	}

	public Set<Yng_MotorizedExterior> getMotorizedExterior() {
		return motorizedExterior;
	}

	public void setMotorizedExterior(Set<Yng_MotorizedExterior> motorizedExterior) {
		this.motorizedExterior = motorizedExterior;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

}
