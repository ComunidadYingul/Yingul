package com.valework.yingul.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


import com.valework.yingul.model.Yng_Sound;

public interface SoundDao extends CrudRepository<Yng_Sound, Long>{
	Yng_Sound findByName(String name);
	List<Yng_Sound> findAll();
	Yng_Sound findBySoundId(int soundId);	


}
