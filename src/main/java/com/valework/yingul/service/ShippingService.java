package com.valework.yingul.service;

import java.util.List;

import com.valework.yingul.model.Yng_Shipping;



public interface ShippingService {
	List<Yng_Shipping> findAll();
	Yng_Shipping findByShippuingId(Long id);

}
