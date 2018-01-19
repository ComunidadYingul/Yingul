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
@Table(name="mot_sound")
public class Yng_MotorizedSound {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long motorizedSoundId;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "motorized_id")
	@JsonBackReference(value="motorized_id")
    private Yng_Motorized motorized;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sound_id")	
    private Yng_Sound sound ;

	public long getMotorizedSoundId() {
		return motorizedSoundId;
	}

	public void setMotorizedSoundId(long motorizedSoundId) {
		this.motorizedSoundId = motorizedSoundId;
	}

	public Yng_Motorized getMotorized() {
		return motorized;
	}

	public void setMotorized(Yng_Motorized motorized) {
		this.motorized = motorized;
	}

	public Yng_Sound getSound() {
		return sound;
	}

	public void setSound(Yng_Sound sound) {
		this.sound = sound;
	}

	@Override
	public String toString() {
		return "Yng_MotorizedSound [motorizedSoundId=" + motorizedSoundId + ", motorized=" + motorized + ", sound="
				+ sound + "]";
	}
	
	
	
}
