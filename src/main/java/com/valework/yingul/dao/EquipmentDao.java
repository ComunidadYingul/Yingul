package com.valework.yingul.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.valework.yingul.model.Yng_Equipment;

public interface EquipmentDao extends CrudRepository<Yng_Equipment, Long> {
	Yng_Equipment findByName(String name);
	List<Yng_Equipment> findAll();
	Yng_Equipment findByEquipmentId(int equipmentId);
	
	
	


}
