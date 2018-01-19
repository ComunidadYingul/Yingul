package com.valework.yingul.service;

import java.util.List;


import com.valework.yingul.model.Yng_Sound;

public interface SoundMotorized {
	List<Yng_Sound>  findAll();
	Yng_Sound findByName(String name);
	Yng_Sound findBySoundId(int soundId);


}
