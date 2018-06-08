package com.valework.yingul.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.valework.yingul.model.Yng_Query;
import com.valework.yingul.model.Yng_User;

public interface QueryDao extends CrudRepository<Yng_Query, Long>{
	List<Yng_Query> findAll();
	List<Yng_Query> findByUserOrderByQueryIdAsc(Yng_User user);
	List<Yng_Query> findByUserAndStatusOrderByQueryId(Yng_User user,String status);
	List<Yng_Query> findBySellerOrderByQueryId(Yng_User user);
	List<Yng_Query> findBySellerAndStatusOrderByQueryId(Yng_User user,String status);
	Yng_Query findByQueryId(long queryId);
}
