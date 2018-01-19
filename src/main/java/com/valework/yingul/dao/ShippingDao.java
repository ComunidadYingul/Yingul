package com.valework.yingul.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


import com.valework.yingul.model.Yng_Shipping;

public interface ShippingDao extends CrudRepository<Yng_Shipping, Long>{
	List<Yng_Shipping> findAll();
	Yng_Shipping findByShippingId(Long id);

}
