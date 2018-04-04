package com.valework.yingul.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.valework.yingul.model.Yng_Commission;

public interface CommissionDao extends CrudRepository<Yng_Commission, Long>{
	List<Yng_Commission> findByOrderByCommissionIdDesc();
	Yng_Commission findByToWhoAndWhy(String toWho, String why);
	Yng_Commission findByConditionAndWhy(String condition, String why);
}
