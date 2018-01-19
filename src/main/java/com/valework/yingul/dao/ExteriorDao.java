package com.valework.yingul.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.valework.yingul.model.Yng_Exterior;

public interface ExteriorDao extends CrudRepository<Yng_Exterior, Long>{
	Yng_Exterior findByExteriorId(int exteriorId);
	Yng_Exterior findByName(String name);
	List<Yng_Exterior> findAll();
	

}
