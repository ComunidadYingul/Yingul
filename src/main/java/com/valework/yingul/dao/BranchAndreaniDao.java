package com.valework.yingul.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.valework.yingul.model.Yng_BranchAndreani;

public interface BranchAndreaniDao extends CrudRepository<Yng_BranchAndreani, Long>{
	Yng_BranchAndreani findByCodAndreani(String cod); 
}
