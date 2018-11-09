package com.valework.yingul.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.valework.yingul.model.Yng_Motorized;
import com.valework.yingul.model.Yng_MotorizedConfort;

public interface MotorizedConfortDao extends CrudRepository<Yng_MotorizedConfort,Long>{
	List<Yng_MotorizedConfort> findByMotorized(Yng_Motorized motorized);
}
