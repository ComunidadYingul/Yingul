package com.valework.yingul.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.valework.yingul.model.Yng_Service;

public interface ServiceDao extends CrudRepository<Yng_Service, Long>{
	Yng_Service findByServiceId(Long id);
	List<Yng_Service> findAll();
}
