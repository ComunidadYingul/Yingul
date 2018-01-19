package com.valework.yingul.service;

import java.util.List;

import com.valework.yingul.model.Yng_Exterior;

public interface ExteriorMotorized {
	List<Yng_Exterior> findAll();
	Yng_Exterior findByExteriorId(int exteriorId);
	Yng_Exterior findByName(String name);

}
