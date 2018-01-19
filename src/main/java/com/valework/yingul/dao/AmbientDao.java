package com.valework.yingul.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.valework.yingul.model.Yng_Ambient;

public interface AmbientDao extends CrudRepository<Yng_Ambient, Long>{
	Yng_Ambient findByAmbientId(int ambientId);
	Yng_Ambient findByName(String name);
	List<Yng_Ambient> findAll();	

}
