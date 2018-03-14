package com.valework.yingul.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.valework.yingul.model.Yng_Province;

public interface ProvinceDao extends CrudRepository<Yng_Province, Long>{
	Yng_Province findByName(String name);
	List<Yng_Province> findAll();
	Yng_Province findByProvinceId(int provinceId);
	List<Yng_Province> 	findByOrderByNameAsc();	
}
