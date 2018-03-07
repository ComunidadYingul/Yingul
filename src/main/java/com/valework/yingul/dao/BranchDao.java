package com.valework.yingul.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.valework.yingul.model.Yng_Branch;

public interface BranchDao extends CrudRepository<Yng_Branch, Long>{
	List<Yng_Branch> findAll();
	Yng_Branch findByBranchId(Long id);
}
