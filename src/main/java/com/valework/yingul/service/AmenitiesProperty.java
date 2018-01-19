package com.valework.yingul.service;

import java.util.List;

import com.valework.yingul.model.Yng_Amenities;


public interface AmenitiesProperty {
	List<Yng_Amenities>findAll();
	Yng_Amenities findByName(String name);
	Yng_Amenities findByAmenitiesId(int amenitiesId);
	

}
