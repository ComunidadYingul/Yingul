package com.valework.yingul.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.valework.yingul.model.Yng_Confort;



public interface ConfortDao extends CrudRepository<Yng_Confort, Long> {
	Yng_Confort findByName(String name);
	List<Yng_Confort>findAll();
	Yng_Confort findByConfortId(int confortId);
}


 