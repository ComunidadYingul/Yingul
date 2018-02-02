package com.valework.yingul.dao;

import org.springframework.data.repository.CrudRepository;
import com.valework.yingul.model.Yng_City;
import java.util.List;

public interface CityDao extends CrudRepository<Yng_City, Long>{
	Yng_City findByName(String name);
	List<Yng_City> findAll();
	Yng_City findByCityId(int cityId);
	List<Yng_City> findByOrderByNameAsc();
	
}
