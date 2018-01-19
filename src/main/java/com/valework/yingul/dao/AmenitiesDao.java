package com.valework.yingul.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.valework.yingul.model.Yng_Amenities;

public interface AmenitiesDao extends CrudRepository<Yng_Amenities, Long> {
	Yng_Amenities findByAmenitiesId(int amenitiesId);
	Yng_Amenities findByName(String name);
	List<Yng_Amenities> findAll();

}
