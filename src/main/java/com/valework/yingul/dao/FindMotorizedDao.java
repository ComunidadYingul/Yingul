package com.valework.yingul.dao;

import org.springframework.data.repository.CrudRepository;
import com.valework.yingul.model.Yng_FindMotorized;

public interface FindMotorizedDao extends CrudRepository<Yng_FindMotorized, Long>{
	Yng_FindMotorized findByCategoryId(Long categoryId);
}
