package com.valework.yingul.service;

import java.util.List;

import com.valework.yingul.model.Yng_Ambient;

public interface AmbientProperty {
	List<Yng_Ambient> findAll();	
	Yng_Ambient findByName(String name);
	Yng_Ambient findByAmbientId(int ambientId);

}
