package com.valework.yingul.dao;

import org.springframework.data.repository.CrudRepository;
import java.util.List;
import com.valework.yingul.model.Yng_Property;

public interface PropertyDao extends CrudRepository<Yng_Property, Long>{
	Yng_Property findByPropertyId(Long id);
	List<Yng_Property> findAll();
}
