package com.valework.yingul.dao;

import org.springframework.data.repository.CrudRepository;
import com.valework.yingul.model.Yng_User;
import com.valework.yingul.model.Yng_XubioClient;

public interface XubioClientDao extends CrudRepository<Yng_XubioClient, Long>{
	Yng_XubioClient findByUser(Yng_User user);
}
