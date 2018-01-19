package com.valework.yingul.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.valework.yingul.model.Yng_Business;


public interface BusinessDao extends  CrudRepository<Yng_Business, Long>{
	List<Yng_Business> findAll();
}
