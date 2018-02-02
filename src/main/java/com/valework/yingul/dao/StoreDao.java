package com.valework.yingul.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.valework.yingul.model.Yng_Store;
import com.valework.yingul.model.Yng_User;

public interface StoreDao extends CrudRepository<Yng_Store, Long>{
	Yng_Store findByName(String name);
	Yng_Store findByUser(Yng_User user);
	List<Yng_Store> findAll();
}
