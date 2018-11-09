package com.valework.yingul.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.valework.yingul.model.Yng_Motorized;
import com.valework.yingul.model.Yng_MotorizedEquipment;

public interface MotorizedEquipmentDao extends CrudRepository<Yng_MotorizedEquipment, Long>{
	List<Yng_MotorizedEquipment> findByMotorized(Yng_Motorized motorized);
}
