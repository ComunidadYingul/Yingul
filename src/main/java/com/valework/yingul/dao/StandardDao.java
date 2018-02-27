package com.valework.yingul.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.valework.yingul.model.Yng_Standard;

public interface StandardDao  extends CrudRepository<Yng_Standard, Long>{
	List<Yng_Standard> findAll();
	Yng_Standard findByStandardId(Long id);
	Yng_Standard findByKey(String key);
}
