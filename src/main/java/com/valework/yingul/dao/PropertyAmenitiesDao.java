package com.valework.yingul.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.valework.yingul.model.Yng_Property;
import com.valework.yingul.model.Yng_PropertyAmenities;

public interface PropertyAmenitiesDao extends CrudRepository<Yng_PropertyAmenities, Long> {
	List<Yng_PropertyAmenities> findByProperty(Yng_Property property);
}
