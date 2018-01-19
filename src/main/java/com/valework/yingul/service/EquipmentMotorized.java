package com.valework.yingul.service;

import java.util.List;

import com.valework.yingul.model.Yng_Equipment;

public interface EquipmentMotorized {
	List<Yng_Equipment> findAll();
	Yng_Equipment findByName(String name);
	Yng_Equipment findByEquipmentId(int equipmentId);
	
}
