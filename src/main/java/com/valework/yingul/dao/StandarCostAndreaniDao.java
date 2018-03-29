package com.valework.yingul.dao;



import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.valework.yingul.model.Yng_StandarCostAndreani;

public interface StandarCostAndreaniDao extends CrudRepository<Yng_StandarCostAndreani, Long>{
	Yng_StandarCostAndreani  findByStandarCostAndreaniId(Long id);
	List<Yng_StandarCostAndreani> findAll();
}
