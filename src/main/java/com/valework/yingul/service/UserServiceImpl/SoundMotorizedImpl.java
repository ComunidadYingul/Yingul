package com.valework.yingul.service.UserServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valework.yingul.dao.SoundDao;
import com.valework.yingul.model.Yng_Sound;
import com.valework.yingul.service.SoundMotorized;

@Service
public class SoundMotorizedImpl implements SoundMotorized {
	@Autowired
	private SoundDao soundDao;

	
	public List<Yng_Sound> findAll() {
		// TODO Auto-generated method stub
		return this.soundDao.findAll();
	}


	public Yng_Sound findByName(String name) {
		// TODO Auto-generated method stub
		return this.soundDao.findByName(name);
	}


	public Yng_Sound findBySoundId(int soundId) {
		// TODO Auto-generated method stub
		return this.findBySoundId(soundId);
	}
	 
	
}
