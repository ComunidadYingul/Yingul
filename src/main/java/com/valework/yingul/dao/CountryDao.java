package com.valework.yingul.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.valework.yingul.model.Yng_Country;

public interface CountryDao extends CrudRepository<Yng_Country, Long>{
	List<Yng_Country> 	findByOrderByNameAsc();
	Yng_Country findByCountryId(int countryId);
	
}
