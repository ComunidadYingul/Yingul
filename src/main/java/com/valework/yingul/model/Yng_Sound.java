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
public class Yng_Sound {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "soundId", nullable = false, updatable = false)
    private int soundId;
	private String name;
	
	
	@OneToMany(mappedBy = "sound", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonBackReference
    private Set<Yng_MotorizedSound> motorizedSound= new HashSet<>();


	public int getSoundId() {
		return soundId;
	}


	public void setSoundId(int soundId) {
		this.soundId = soundId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Set<Yng_MotorizedSound> getMotorizedSound() {
		return motorizedSound;
	}


	public void setMotorizedSound(Set<Yng_MotorizedSound> motorizedSound) {
		this.motorizedSound = motorizedSound;
	}


	@Override
	public String toString() {
		return "Yng_Sound [soundId=" + soundId + ", name=" + name + ", motorizedSound=" + motorizedSound + "]";
	}
    
    
    
	
}
