package com.valework.yingul.service;

import java.util.List;

import com.valework.yingul.model.Yng_Confort;

public interface ConfortMotorized {
	
	List<Yng_Confort> findAll();
	Yng_Confort findByName(String name);
	Yng_Confort findByConfortId(int confortId);
	
}
