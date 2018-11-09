package com.valework.yingul.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.valework.yingul.model.Yng_Property;
import com.valework.yingul.model.Yng_PropertyAmbient;

public interface PropertyAmbientDao extends CrudRepository<Yng_PropertyAmbient,Long>{
	List<Yng_PropertyAmbient> findByProperty(Yng_Property property);
}
