package com.valework.yingul.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.valework.yingul.model.Yng_Query;

public interface QueryDao extends CrudRepository<Yng_Query, Long>{
	List<Yng_Query> findAll();
	Yng_Query findByQueryId(long queryId);
}
