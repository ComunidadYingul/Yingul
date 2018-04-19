package com.valework.yingul.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.valework.yingul.model.Yng_Item;
import com.valework.yingul.model.Yng_Service;

public interface ServiceDao extends CrudRepository<Yng_Service, Long>{
	Yng_Service findByServiceId(Long id);
	//Yng_Service findByYng_Item(Yng_Item item);
	List<Yng_Service> findAll();
}
