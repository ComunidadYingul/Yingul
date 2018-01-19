package com.valework.yingul.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.valework.yingul.model.Yng_Security;

public interface SecurityDao extends CrudRepository<Yng_Security, Long>{
	Yng_Security findBySecurityId(int securityId);
	Yng_Security findByName(String name);
	List<Yng_Security> findAll();

}
