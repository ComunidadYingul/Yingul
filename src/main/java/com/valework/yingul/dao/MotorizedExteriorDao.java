package com.valework.yingul.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.valework.yingul.model.Yng_Motorized;
import com.valework.yingul.model.Yng_MotorizedExterior;

public interface MotorizedExteriorDao extends CrudRepository<Yng_MotorizedExterior, Long>{
	List<Yng_MotorizedExterior> findByMotorized(Yng_Motorized motorized);
}
