package com.valework.yingul.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.valework.yingul.model.Yng_Motorized;
import com.valework.yingul.model.Yng_MotorizedSound;

public interface MotorizedSoundDao extends CrudRepository<Yng_MotorizedSound, Long>{
	List<Yng_MotorizedSound> findByMotorized(Yng_Motorized motorized);
}
